What are Identities and Identity-Based Attacks?

Identities refer to all entities, whether human or machine, that can access resources within an organization's digital ecosystem. These entities include employees, IT administrators, third-party vendors, devices, and applications. In modern enterprise environments, identities serve as a critical security boundary. Managing and securing these identities is paramount to ensuring data confidentiality, system integrity, and organizational security.

Identity-based attacks are a class of cyber threats aimed at exploiting vulnerabilities in authentication and access control mechanisms. Threat actors often target user credentials, privileged accounts, or devices to gain unauthorized access and compromise organizational assets. Common attack vectors include phishing, credential stuffing, session hijacking, and leveraging weak authentication methods. By successfully exploiting identities, attackers can infiltrate systems, escalate privileges, and conduct malicious activities. Implementing robust identity and access management (IAM) strategies is essential to defend against these evolving threats.

About Our Project

Our project, Defending Against Identity Threats Using Adaptive Authentication, is designed to mitigate identity-based threats by implementing a dynamic and intelligent risk-based authentication system. It leverages zero-trust principles and a risk-scoring algorithm to classify users and devices as either risky or non-risky. By analyzing behavioral and contextual data, the system dynamically adapts authentication requirements to mitigate potential threats. The project addresses five prominent real-time use cases and employs adaptive authentication techniques to provide a robust defense against identity-based attacks.

Key Features

Dynamic Risk-Based Authentication: Moves beyond traditional static username-password methods to a dynamic, context-aware authentication approach.
Anomaly Detection: Continuously monitors user behavior and access patterns to detect suspicious activities or potential compromises.
Zero-Trust Principles: Assumes no entity (user or device) is inherently trustworthy, thus requiring continuous verification.
Device Health Posture: Evaluates the security compliance of devices attempting to access organizational resources.
SOC Dashboard: Provides SOC (Security Operations Center) analysts with an intuitive dashboard to monitor risk scores and respond to threats in real-time.

Use Cases

The project tackles five critical real-time use cases that demonstrate various identity-based threats and their mitigation strategies:

1. Impossible Travel
Detects logins from geographically distant locations within an improbable timeframe.
Uses travel velocity to flag abnormal login attempts.
Prompts Multi-Factor Authentication (MFA) for users exhibiting impossible travel behavior.
2. Anonymity Sign-In
Identifies logins from anonymous browsers (e.g., TOR) or masked IP addresses.
Increases the user's risk score and enforces additional authentication steps.
Triggers mitigative actions like MFA or temporary access restrictions.
3. Atypical User Sign-In Behavior
Monitors user sign-in behavior based on:
Location: Flags logins from unfamiliar geographic locations.
Browser: Detects sign-ins from unknown or unregistered browsers.
Device: Identifies attempts from unrecognized devices.
Dynamically adjusts risk scores and authentication requirements.
4. Compromised Credentials
Detects login attempts using publicly exposed or compromised credentials.
Forces the user to reset their password immediately.
Prevents account takeover through proactive monitoring of breached credentials.
5. Unsafe Device Login
Identifies logins from non-compliant or unregistered devices.
Elevates the device risk score and enforces mitigation measures like MFA.
Prevents compromised devices from accessing sensitive organizational data.

Technology Stack

The project was built using the following technology stack:

Frontend: HTML5, JavaScript, JQuery.
Backend: Java Spring Boot, Maven.
Database: MySQL.
APIs: Microsoft Graph API (for Azure AD and Microsoft Intune integration).
Development Tools: Eclipse IDE, Apache Tomcat (server).

How to Use
Prerequisites:
Ensure the following are installed and configured on your system:

Java JDK 21 or higher.
Maven.
MySQL database.
Apache Tomcat server.
Azure Active Directory (for Graph API integration).
Setup Instructions:
Clone the repository using:
git clone <repository-link>
Configure the database connection in the application.properties file.
Build the project using Maven:
mvn clean install
Deploy the generated WAR file on Apache Tomcat.
Access the application via browser at:
http://<server-address>:<port>
For Azure integration, ensure the Graph API credentials are configured.

Future Scope
The current project demonstrates the power of adaptive authentication in mitigating identity-based attacks across five use cases. In future iterations, the project can be extended to:

Incorporate additional real-time use cases to cover emerging identity threats.
Extend device compliance checks to consider malware presence, operating system versions, and device health posture.
Implement real-time threat response automation by automatically revoking access during high-risk situations.
Support broader identity types such as service accounts, machine identities, and privileged access management (PAM).

Conclusion

The Defending Against Identity Threats Using Adaptive Authentication project is designed to combat the growing threat of identity-based attacks in modern digital ecosystems. By leveraging zero-trust principles, risk-scoring algorithms, and adaptive authentication, the project delivers a proactive and robust security framework. This dynamic and intelligent approach ensures that organizational resources remain safeguarded from unauthorized access while minimizing user friction.
