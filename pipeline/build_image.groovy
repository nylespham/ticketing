pipeline {
    environment {
        USERNAME="xnylesx"
        ACCESS_TOKEN="dckr_pat_nuGt6Ln4l7yp7tNimKCTlNQGS0c"
    }
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
                sh 'echo ${ACCESS_TOKEN} | sudo docker login -u ${USERNAME} --password-stdin'
            }
        }
        stage('Push Image to Repository') {
            steps {
                sh 'sudo docker push nylesx/ticketing:auth'
            }
        }
    }
    post {
        always {
            cleanWs(cleanWhenNotBuilt: false,
                    deleteDirs: true,
                    disableDeferredWipeout: true,
                    notFailBuild: true,
                    patterns: [[pattern: '.gitignore', type: 'INCLUDE'],
                               [pattern: '.propsfile', type: 'EXCLUDE']])
        }
}
