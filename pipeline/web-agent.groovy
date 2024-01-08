pipeline {
    environment {
        USERNAME="xnylesx"
        ACCESS_TOKEN="dckr_pat_nuGt6Ln4l7yp7tNimKCTlNQGS0c"
        CLUSTER_NAME = 'do-nyc3-seattle-nyles-k8s'
        CA_CERTIFICATE= 'LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSURKekNDQWcrZ0F3SUJBZ0lDQm5Vd0RRWUpLb1pJaHZjTkFRRUxCUUF3TXpFVk1CTUdBMVVFQ2hNTVJHbG4KYVhSaGJFOWpaV0Z1TVJvd0dBWURWUVFERXhGck9ITmhZWE1nUTJ4MWMzUmxjaUJEUVRBZUZ3MHlOREF4TURneApNek15TVRaYUZ3MDBOREF4TURneE16TXlNVFphTURNeEZUQVRCZ05WQkFvVERFUnBaMmwwWVd4UFkyVmhiakVhCk1CZ0dBMVVFQXhNUmF6aHpZV0Z6SUVOc2RYTjBaWElnUTBFd2dnRWlNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0SUIKRHdBd2dnRUtBb0lCQVFDd1RpbE1jdUt1ZHNDT2NTQ1N3ZG14aE1waTdMS2lLYmN1VGJSLzBKN0pjcXFwOEpjbApBVGppSlVMd0luOWkzYW5VanFIdXJtSG5CTk0xY3VDL1NzcmR4VWx2SGdMN1h5eGpQYm9CL0hVZWdWZHpxUkZqCm85K3hFTlhhaFkzQ1Ixd2lTeHJBVVRiVFdpLzcyV09lNHpDQzdMamhNQW5VVFJGUXVYU0NVNlIvUHF3dncycDUKTTgrOVJWR0VRTExzYnJLNWNVL2c4STM1amZSRHQrNU1wUk8rTjdJck5XRCtmSmtsbXM1c0txdytEaXZqWkhyTAprYVZ3ZTI5R1hBTVZIVFM3ckZ3MENxaTZ0M2g2aGo4WGdKNTFIbGRoVWtVUHdHV2FoVytieDdhL3lLMXF0WHpICmJXU09IRS84aWZTSFZ0UWJhaXVpa2h1UjQ3NGpxbEtLR3BJVEFnTUJBQUdqUlRCRE1BNEdBMVVkRHdFQi93UUUKQXdJQmhqQVNCZ05WSFJNQkFmOEVDREFHQVFIL0FnRUFNQjBHQTFVZERnUVdCQlRYYXFiK2ZxL2dqQ0xaRHlCQQpnaW5FazkyS0NEQU5CZ2txaGtpRzl3MEJBUXNGQUFPQ0FRRUFIa3hHSEVNZEgzdGhwaFlYWGxiWFdHZmhnRGdxCjZ3cFZ2bTZxVSs5NTYzVGFWYlhrUTFZK3h6bjJnbzF4V1B4dkpYVXBFUkoyancyTDNMSzRZYUhuZlRqL1ErcTYKZ01pVDh6RXZyOHNyaUNHNFRaeCttdHZhZlE3RE9RaytDQjluQU1kUWM1Mm5CVFk4a1FEZU9FVmFNL2FyQ3pGWQpCTDVIa3M4enRKNS9nTVlEaVFHQlhTU3kzRCtKT05HQW9rMXhzUUJwOCtITFZFTVFmSkpEdW1VWlNoTjJTdkcwCnBWaVBSYS90QUNIMEhKYjFGSVgyK1hXMGRuK3RLYmRCM2VPeGpRajVMcU02WlZmSEhDUEF4cG1ycmV4NTF1dFYKS2l6UUI4Q2tWV0NIc3dHWk4xb3RHRWFiMEplZ1hMTjd4Q01UZHpwWmhkZkZsVy9WVmRVZlNYWDVWZz09Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K'
        SERVICE ='./k8s/client-deployment.yaml'
        NAMESPACE = 'default'
        SERVER_URL = 'https://4a4bcd4c-0e87-48cc-9fae-14901d14f306.k8s.ondigitalocean.com'
        CONTEXT_NAME = 'do-nyc3-seattle-nyles-k8s'
        CREDENTIALS_ID = 'k8s'

    }
    agent any
    stages {
        stage('Build Image') {
            steps {
                sh 'sudo docker build -t auth:latest -f dockerfiles/Dockerfile . --build-arg SERVICE_NAME=client'
            }
        }
        stage('Build Tag Image') {
            steps {
                sh 'sudo docker tag auth:latest xnylesx/ticketing:client'
            }
        }
        stage('Login with Access Token') {
            steps {
                sh 'echo ${ACCESS_TOKEN} | sudo docker login -u ${USERNAME} --password-stdin'
            }
        }
        stage('Push Image to Repository') {
            steps {
                sh 'sudo docker push xnylesx/ticketing:client'
            }
        }
        stage('Deploy to Kubernetes Cluster') {
            steps {
                sh 'kubectl apply -f ${SERVICE}'
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
