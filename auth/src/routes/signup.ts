import express, { Request, Response } from "express";
import { body } from "express-validator";
import { DatabaseConnectionError } from "../errors/database-connection-error";
import { User } from "../models/user";
import { BadRequestError } from "../errors/bad-request-error";
import jwt from "jsonwebtoken";
import { validateRequest } from "../middlewares/validate-request";

const router = express.Router();

router.post("/api/users/signup", [
    body("email")
        .isEmail()
        .withMessage("Email must be valid"),
    body("password")
        .trim()
        .isLength({ min: 4, max: 20 })
        .withMessage("Password must be between 4 and 20 characters")
    ],
    validateRequest, 
    async (req: Request, res: Response) => {

        const { email, password } = req.body;

        const existingUser = await User.findOne({ email });

        if (existingUser) {
            throw new BadRequestError("Email in use");
        }

        const user = User.build({ email, password });
        await user.save();

        // Generate JWT
        const userJWT = jwt.sign({
            user_id: user.id,
            email: user.email
        }, process.env.JWT_TOKEN!,{
            expiresIn: "1h"
        })

        // Store it on session object
        req.session = {
            jwt: userJWT
        }
        res.status(201).send(user);

        throw new DatabaseConnectionError();
        res.send({});

});

export { router as signupRouter }