package com.yapp.ios2.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class AwsSnsService {

    private AmazonSNS snsClient;

    private SetSMSAttributesRequest setRequest;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.sns.topic.arn")
    private String arn;

    @Value("${cloud.aws.region.static}")
    private String region;

    PublishRequest request = new PublishRequest();



    @PostConstruct
    public void setDefaultSmsAttributes() {

        AWSCredentials credentials = new BasicAWSCredentials("AKIAY7EBN7YWKAT35BKH", "UkcHAqz+IrBXXGBetvwweMrKxYk2yKIH4Jv7csNJ");
//        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSNSClientBuilder builder = AmazonSNSClientBuilder.standard();

        snsClient = AmazonSNSClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
//        setRequest = new SetSMSAttributesRequest()
//                .addAttributesEntry("DefaultSenderID", "007")
//                .addAttributesEntry("MonthlySpendLimit", "1")
//                .addAttributesEntry("DeliveryStatusIAMRole",
//                        arn)
//                .addAttributesEntry("DeliveryStatusSuccessSamplingRate", "10")
//                .addAttributesEntry("DefaultSMSType", "Transactional")
//                .addAttributesEntry("UsageReportS3Bucket", "sns-sms-daily-usage");
//        snsClient.setSMSAttributes(setRequest);
//        Map<String, String> myAttributes = snsClient.getSMSAttributes(new GetSMSAttributesRequest())
//                .getAttributes();
//        System.out.println("My SMS attributes:");
//        for (String key : myAttributes.keySet()) {
//            System.out.println(key + " = " + myAttributes.get(key));
//        }
    }

    public String sendSMS(String message, String phoneNumber){
//        setRequest = new SetSMSAttributesRequest()
//            .addAttributesEntry("DefaultSenderID", "007")
//            .addAttributesEntry("MonthlySpendLimit", "1")
////            .addAttributesEntry("DeliveryStatusIAMRole", arn)
//            .addAttributesEntry("DeliveryStatusSuccessSamplingRate", "10")
//            .addAttributesEntry("DefaultSMSType", "Promotional")
//            .addAttributesEntry("UsageReportS3Bucket", "sns-sms-daily-usage");

        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("01095233114") //The sender ID shown on the device.
                .withDataType("String"));
//        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
//                .withStringValue("0.5") //Sets the max price to 0.50 USD.
//                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Transactional") //Sets the type to promotional.
                .withDataType("String"));

        PublishResult result = snsClient.publish(
                new PublishRequest()
                        .withMessage(message)
                        .withPhoneNumber(phoneNumber)
                        .withMessageAttributes(smsAttributes));
        return result.toString();
    }

//    @PostConstruct
//    public void setSnsClient(){
//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//
//        snsClient = AmazonSNSClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(this.region)
//                .build();
//
//        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
//                .withStringValue("90") //The sender ID shown on the device.
//                .withDataType("String"));
//        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
//                .withStringValue("0.50") //Sets the max price to 0.50 USD.
//                .withDataType("Number"));
//        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
//                .withStringValue("Promotional") //Sets the type to promotional.
//                .withDataType("String"));
//
//    }

}