pipeline {
    agent any
    stages {
        stage('Login Docker Hub') {
            steps {
                docker login -u xnylesx -p Strator@202301
                echo 'Login successfully!'
            }
        }
    }
}
