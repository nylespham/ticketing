pipeline {
    agent any
    stages {
        stage('Test docker command') {
            steps {
                docker ps
                echo 'Login successfully!'
            }
        }
        stage('Login Docker Hub') {
            steps {
                docker build -t auth:latest -f Dockerfile .
                echo 'Build successfully!'
            }
        }
    }
}
