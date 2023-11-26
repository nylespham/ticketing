environment {
  password = "Strator@202301"
  username = "xnylesx"
}
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
                docker login --password-stdin ${password} -u ${username}  
                echo 'Login successfully!'
            }
        }
    }
}
