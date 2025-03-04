package br.com.spring.sale.service

import br.com.digital.order.exceptions.AWSServiceException
import br.com.spring.sale.exceptions.AWSClientException
import br.com.spring.sale.exceptions.IllegalArgumentCustomException
import br.com.spring.sale.exceptions.ResourceNotFoundException
import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.io.InputStream
import java.net.URI
import java.net.URL

@Service
class S3Service {

    @Autowired
    private lateinit var s3client: AmazonS3

    @Value("\${s3.bucket}")
    private val bucketName: String? = null

    fun uploadFile(
        file: MultipartFile,
        path: String,
    ): URL {
        try {
            val originalName = file.originalFilename
            val extension = FilenameUtils.getExtension(originalName)
            val fileName: String = org.joda.time.Instant.now().toDate().time.toString() + "." + extension
            val inputStream: InputStream = file.inputStream
            val contentType: String? = file.contentType
            val s3Path = "$path$fileName"
            return processFile(
                inputStream = inputStream,
                fileName = s3Path,
                contentType = contentType,
                contentLength = file.size
            )
        } catch (e: AmazonServiceException) {
            throw AWSServiceException(e.message)
        } catch (e: AmazonClientException) {
            throw AWSClientException(e.message)
        } catch (e: IOException) {
            throw IllegalArgumentCustomException(exception = e.message)
        }
    }

    private fun processFile(
        inputStream: InputStream,
        fileName: String,
        contentType: String?,
        contentLength: Long
    ): URL {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = contentType
        objectMetadata.contentLength = contentLength
        s3client.putObject(bucketName, fileName, inputStream, objectMetadata)
        return s3client.getUrl(bucketName, fileName)
    }

    fun updateFile(
        url: String,
        multipartFile: MultipartFile,
        path: String
    ): URL {
        try {
            val uri = URI(url)
            val bucketNameFind = uri.host.split(".")[0]
            val nativeImage = uri.path.substring(1)
            if (s3client.doesObjectExist(bucketNameFind, nativeImage)) {
                deleteFile(url)
                return uploadFile(file = multipartFile, path = path)
            } else {
                throw ResourceNotFoundException(message = FILE_NOT_FOUND)
            }
        } catch (a: AmazonClientException) {
            throw RuntimeException(a.message)
        }
    }

    fun deleteFile(url: String) {
        try {
            val uri = URI(url)
            val bucketNameFind = uri.host.split(".")[0]
            val nativeImage = uri.path.substring(startIndex = 1)
            if (bucketName == bucketNameFind) {
                s3client.deleteObject(DeleteObjectRequest(bucketName, nativeImage))
            }
        } catch (e: Exception) {
            throw ResourceNotFoundException(message = FILE_NOT_FOUND)
        }
    }

    companion object {
        const val NAME_IMAGE_NOT_EMPTY = "Image not empty"
        const val FILE_NOT_FOUND = "File Not Found"
    }
}
