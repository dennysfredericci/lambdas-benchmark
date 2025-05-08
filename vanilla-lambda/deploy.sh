mvn clean install
aws s3 cp ./target/vanilla-lambda.jar s3://lambda-deployment-fredericci
aws lambda update-function-code --function-name vanilla-lambda --s3-bucket lambda-deployment-fredericci --s3-key vanilla-lambda.jar

