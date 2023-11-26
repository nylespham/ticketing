environment {
    USERNAME="xnylesx"
}
pipeline {
    agent any
    stages {
        stage('Build Image') {
            steps {
                sh 'sudo docker build -t auth:latest -f Dockerfile . --build-arg SERVICE_NAME=auth'
            }
        }
        stage('Build Tag Image') {
            steps {
                sh 'sudo docker tag auth:latest nylesx/ticketing:auth'
            }
        }
        stage('Login with Access Token') {
            steps {
                sh 'read -s -p "Enter Docker Hub personal access token: " ACCESS_TOKEN'
                sh 'echo "$ACCESS_TOKEN" | docker login -u $USERNAME --password-stdin'
                sh 'unset ACCESS_TOKEN'
            }
        }
        stage('Push Image to Repository') {
            steps {
                sh 'sudo docker push nylesx/ticketing:auth'
            }
        }
    }
}
