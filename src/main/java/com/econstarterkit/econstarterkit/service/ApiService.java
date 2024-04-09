package com.econstarterkit.econstarterkit.service;

import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.repository.ProblemRepository;
import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.Institution;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

@Service
@RequiredArgsConstructor
public class ApiService {
    @Value("${koreaSecuritiesDepository.apikey}")
    private String apiKey_ksd;
    @Value("${koreaBank.apikey}")
    private String apiKey_kb;

    private final ProblemRepository problemRepository;

    public void getApi() throws IOException, ParserConfigurationException, SAXException {
        getApi_koreaSecuritiesDepository();
        getApi_koreaBank();
    }

    private void getApi_koreaBank() throws IOException {
        for (int i = 0; i < 19055; i++) {
            String text = String.valueOf((char)(0xAC00 + i));
            String apiUrl = "https://ecos.bok.or.kr/api/StatisticWord/" + apiKey_kb + "/json/kr/1/100/" + text;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(response);
        }
    }

    public void getApi_koreaSecuritiesDepository() throws IOException, SAXException, ParserConfigurationException {
        StringBuilder urlBuilder = new StringBuilder("https://api.seibro.or.kr/openapi/service/FnTermSvc/getFinancialTermMeaning"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey_ksd); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("term", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*LIKE 검색*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*숫자로 관리*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("2000", "UTF-8")); /*숫자로 관리*/

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(urlBuilder.toString());

        // 제일 첫번째 태그
        doc.getDocumentElement().normalize();

        // 파싱할 tag
        NodeList items = doc.getElementsByTagName("item");
        System.out.println(items.getLength());

        for (int temp = 0; temp < items.getLength(); temp++) {
            Node nNode = items.item(temp);

            Element eElement = (Element) nNode;

            Problem problem = Problem.builder()
                    .description(getTagValue("ksdFnceDictDescContent", eElement))
                    .correctWord(getTagValue("fnceDictNm", eElement))
                    .difficulty(Difficulty.NOT_CHECK)
                    .institution(Institution.KOREA_SECURITIES_DEPOSITORY)
                    .build();

            problemRepository.save(problem);
        }
    }

    public static String getTagValue(String tag, Element eElement) {

        //결과를 저장할 result 변수 선언
        String result = "";

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();

        result = nlList.item(0).getTextContent();

        result = result.replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&nbsp;", "")
                .replaceAll("&amp;", "")
                .replaceAll("&quot;", "")
                .replaceAll("&middot;", "")
                .replaceAll("&divide;", "");
        result = result.replaceAll("<[^>]+>", "").replaceAll("\n", "");

        return result;
    }
}
