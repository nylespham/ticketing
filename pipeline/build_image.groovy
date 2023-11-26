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
        stage('Push Image to Repository') {
            steps {
                sh 'sudo docker push nylesx/ticketing:auth'
            }
        }
    }
}
