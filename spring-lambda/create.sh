mvn clean install
mv ./target/spring-lambda-0.0.1-SNAPSHOT-aws.jar ./target/spring-lambda.jar
aws s3 cp ./target/spring-lambda.jar s3://lambda-deployment-fredericci
sam deploy ./template.yaml --stack-name lambdas-benchmark --capabilities CAPABILITY_IAM