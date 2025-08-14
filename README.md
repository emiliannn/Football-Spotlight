# ⚽ Football Spotlight – A Full-Stack Football App

Football Spotlight is a full-stack application designed to provide a comprehensive view of the football ecosystem. It models relationships between key entities such as:

- 🧑‍💼 Managers  
- 👥 Users  
- 🧍 Players  
- 🏟️ Football Clubs  
- 📄 Contracts  

The database architecture supports advanced analyses, including:

- 📊 Player performance tracking  
- 📑 Contract management  
- 🏆 Club performance evaluation 

---

## 🔄 Apache Camel Integration

To enhance backend integration, I implemented Apache Camel’s Enterprise Integration Patterns (EIPs).

### 🛠️ Example: XML to JSON Transformation Route

A custom Camel route handles HTTP GET requests to `/json/players` using the **servlet** component. Here's how it works:

1. ➡️ Request is routed to `direct:xmlToJSON`
2. 📥 Messages are consumed from `seda:xmlToJSON`
3. 📡 XML player data is retrieved via `getPlayers()` from `ExistService`
4. 🔄 Data is processed by `XML_JSON_Processor`

### 🧪 XML_JSON_Processor Highlights

- Checks if the body is a `RemoteXMLResource`
- Extracts and optionally cleans XML content
- Uses **Jackson's XmlMapper** to convert XML to Java objects
- Converts Java objects to JSON using **Jackson's ObjectMapper**
- Sets the JSON output as the response body

✅ Result: XML player data is transformed into JSON and returned to the client.

---

## 🧰 Tech Stack

| Layer     | Technologies Used                     |
|-----------|----------------------------------------|
| Backend   | Spring, XML DB (local), ExistDB        |
| Frontend  | Angular                                |

---

## 📂 Source Code & Presentation

For the full project code and additional details, including presentation slides, check out the repository.

Thanks for checking out Football Spotlight! 🙌
