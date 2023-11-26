pipeline {
    environment {
        USERNAME="xnylesx"
        ACCESS_TOKEN="dckr_pat_nuGt6Ln4l7yp7tNimKCTlNQGS0c"
        PROJECT_ID = '<YOUR_PROJECT_ID>'
        CLUSTER_NAME = '<YOUR_CLUSTER_NAME>'
        LOCATION = '<YOUR_CLUSTER_LOCATION>'
        CREDENTIALS_ID = '<YOUR_CREDENTIAS_ID>'
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
                sh 'sudo docker tag auth:latest xnylesx/ticketing:auth'
            }
        }
        stage('Login with Access Token') {
            steps {
                sh 'echo ${ACCESS_TOKEN} | sudo docker login -u ${USERNAME} --password-stdin'
            }
        }
        stage('Push Image to Repository') {
            steps {
                sh 'sudo docker push xnylesx/ticketing:auth'
            }
        }
        stage('Deploy to GKE Cluster') {
            steps{
                step([
                $class: 'KubernetesEngineBuilder',
                projectId: env.PROJECT_ID,
                clusterName: env.CLUSTER_NAME,
                location: env.LOCATION,
                manifestPattern: 'manifest.yaml',
                credentialsId: env.CREDENTIALS_ID,
                verifyDeployments: true])
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
}
