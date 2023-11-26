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
                docker login --password-stdin -u 'xnylesx' -p 'Strator@202301'
                echo 'Login successfully!'
            }
        }
    }
}
