pipeline {
    agent any
    stages {
        stage('Build Image') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
