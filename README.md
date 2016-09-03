#JText
A Java-based SMTP/SMS gateway client.

##What is it?
It's a cross-platform Java-based application which runs within a terminal, such as Command Prompt on Windows or Bash on Mac OS X. JText allows you to be able to send texts to any cellular device from your own computer.

##How does it work?
JText sends SMTP messages to the target devices' carrier's SMS gateway. The gateway converts the SMTP message into an SMS message, which is then sent to the target device's phone by the gateway.

For JText to work, you must give the application the credentials and DNS/IP addresses for your preferred SMTP server and POP3 server. It uses the SMTP server for sending messages, and your POP3 server for receiving responses from cellular devices. JText caches all sent SMTP messages and received POP3 messages into a local file, and deletes them on the remote server, allowing for faster connections and clutterless inboxes. 

##How do I run it?
You can download it in the [Downloads](#downloads) section. Once downloaded, run <code>java -jar sms.jar</code> within your preferred terminal in the directory that the downloaded jar file is in.

Within the application shell, run the following commands to begin:<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>login</code> to login to your SMTP/POP3 servers. For reference:<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gmail SMTP server: <code>smtp.gmail.com:587</code><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gmail POP3 server: <code>pop.gmail.com:995</code><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Start texting away! Type <code>help</code> for a list of commands. See [Documentation](#documentation) for more info.

## Downloads
V1.0 Stable Download: https://github.com/TitaniumSapphire/Console-SMS-Client/raw/master/build/sms.jar

## Documentation
All documentation can be found within the app. Type "help" in the shell to get a list of commands. Type <code>man \<command\></code> to get detailed documentation for a specific command.

##Licence
Under the [GNU General Public Licence 3.0](LICENCE.md).
<b>Copyright © 2016 Lucas Baizer</b>
