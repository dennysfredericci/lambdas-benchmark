mvn clean install
mv ./target/spring-lambda-0.0.1-SNAPSHOT-aws.jar ./target/spring-lambda.jar
aws s3 cp ./target/spring-lambda.jar s3://lambda-deployment-fredericci
aws lambda update-function-code --function-name spring-lambda --s3-bucket lambda-deployment-fredericci --s3-key spring-lambda.jar

# aws lambda get-function-configuration \
#  --function-name spring-lambda:2