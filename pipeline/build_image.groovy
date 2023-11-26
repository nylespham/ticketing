pipeline {
    agent any
    stages {
        stage('Login Docker Hub') {
            steps {
                docker login --password-stdin -u xnylesx -p Strator@202301
                echo 'Login successfully!'
            }
        }
    }
}
