package br.com.spring.sale.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {

    @Value("\${aws.access_key_id}")
    private val awsId: String? = null

    @Value("\${aws.secret_access_key}")
    private val awsKey: String? = null

    @Bean
    fun s3client(): AmazonS3 {
        val awsCred = BasicAWSCredentials(awsId, awsKey)
        return AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1)
            .withCredentials(AWSStaticCredentialsProvider(awsCred)).build()
    }
}
