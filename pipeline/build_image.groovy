pipeline {
    agent any
    stages {
        stage('Test docker command') {
            steps {
                sh 'docker ps'
                echo 'Login successfully!'
            }
        }
        stage('Login Docker Hub') {
            steps {
                sh 'docker build -t auth:latest -f Dockerfile .'
                echo 'Build successfully!'
            }
        }
    }
}
