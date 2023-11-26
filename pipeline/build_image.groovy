pipeline {
    agent any
    stages {
        stage('Test docker command') {
            steps {
                sh 'sudo docker ps'
                echo 'Login successfully!'
            }
        }
        stage('Login Docker Hub') {
            steps {
                sh 'sudo docker build -t auth:latest -f Dockerfile . --build-arg SERVICENAME=auth'
                echo 'Build successfully!'
            }
        }
    }
}
