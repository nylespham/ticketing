import express from "express";
import { json } from "body-parser";
import "express-async-errors";
import mongoose from "mongoose";
import cookieSession from "cookie-session";

import { currentUserRouter } from "./routes/current_user";
import { signinRouter } from "./routes/signin";
import { signoutRouter } from "./routes/signout";
import { signupRouter } from "./routes/signup";
import { errorHandler } from "./middlewares/error-handler";
import { NotFoundError } from "./errors/not-found-error";


const port = 3000;
const app = express();

app.set("trust proxy", true);
app.use(json());
app.use(cookieSession({
    signed: false,
    secure: true
}));

app.use(currentUserRouter);
app.use(signinRouter);
app.use(signoutRouter);
app.use(signupRouter);

app.all("*", async (req, res) => {
    throw new NotFoundError();
});

app.use(errorHandler);

const start = async () => {

    if (!process.env.JWT_TOKEN) {
        throw new Error("JWT_TOKEN must be defined");
    }

    try {
        await mongoose.connect("mongodb://auth-mongo-srv:27017/auth");
        console.log("Connected to mongodb");
    } catch (err){
        console.error(err);
    }
    app.listen(3000, () => {
        console.log(`Listening on port ${port}`);
    });
}

start();

