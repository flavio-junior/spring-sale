package br.com.spring.sale.service

import br.com.spring.sale.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailException
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailService {

    @Value("\${spring.mail.username}")
    private val emailFrom: String? = null

    @Autowired
    private val emailSender: JavaMailSender? = null

    fun sendEmailToConfirmation(to: String, subject: String, body: String) {
        try {
            val message = SimpleMailMessage()
            message.from = emailFrom
            message.setTo(to)
            message.subject = subject
            message.text = body
            emailSender?.send(message)
        } catch (e: MailException) {
            throw ResourceNotFoundException("Failed to send email")
        }
    }
}
