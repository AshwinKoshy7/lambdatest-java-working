pipeline {
    agent any

    tools {
        maven 'Maven 3.9'   // Ensure this is configured in Jenkins → Global Tools
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/AshwinKoshy7/lambdatest-java-working.git'
            }
        }

        stage('Build & Run Tests') {
            steps {
                sh 'mvn clean test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
