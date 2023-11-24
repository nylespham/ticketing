FROM node:alpine

ARG SERVICE_NAME=default

RUN mkdir -p /ticketing/${SERVICE_NAME}

WORKDIR /ticketing/${SERVICE_NAME}

COPY ${SERVICE_NAME}/src/package.json .

RUN npm install

CMD [ "npm", "start" ]