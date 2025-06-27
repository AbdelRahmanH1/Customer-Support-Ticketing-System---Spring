package com.system.customer_support_ticketing_system.utils;

public class EmailTemplateBuilder {

    public static String buildResetCodeTemplate(String name, String code) {
        return """
                <html>
                  <body style="font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);">
                      <h2 style="color: #333;">Password Reset Request</h2>
                      <p>Hello <strong>%s</strong>,</p>
                      <p>You requested a password reset. Use the code below to reset your password:</p>
                      <div style="font-size: 24px; font-weight: bold; color: #2b5cff; margin: 20px 0;">%s</div>
                      <p>This code will expire in 15 minutes. If you didn't request this, please ignore the email.</p>
                      <p style="margin-top: 30px;">Thanks,<br>The Support Team</p>
                    </div>
                  </body>
                </html>
                """.formatted(name, code);
    }
}

