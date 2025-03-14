mvn clean install
mv ./target/function.zip ./target/quarkus-lambda.zip
aws s3 cp ./target/quarkus-lambda.zip s3://lambda-deployment-fredericci
aws lambda update-function-code --function-name quarkus-lambda --s3-bucket lambda-deployment-fredericci --s3-key quarkus-lambda.zip

