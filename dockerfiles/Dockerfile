FROM node:alpine

ARG SERVICE_NAME=default

RUN mkdir -p /ticketing/${SERVICE_NAME}

WORKDIR /ticketing/${SERVICE_NAME}

COPY ${SERVICE_NAME}/. .

RUN npm install

CMD [ "npm", "start" ]