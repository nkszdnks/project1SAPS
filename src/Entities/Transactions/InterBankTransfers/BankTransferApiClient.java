package Entities.Transactions.InterBankTransfers;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BankTransferApiClient {

    private static final String BASE_URL = "http://147.27.70.44:3020";
    private final HttpClient client = HttpClient.newHttpClient();

    public JSONObject executeSepa(SepaTransferRequest req) throws Exception {

        JSONObject json = new JSONObject()
                .put("amount", req.getAmount())
                .put("creditor", new JSONObject()
                        .put("name", req.getCreditorName())
                        .put("iban", req.getCreditorIban()))
                .put("creditorBank", new JSONObject()
                        .put("bic", req.getBic())
                        .put("name", req.getBankName()))
                .put("execution", new JSONObject()
                        .put("requestedDate", req.getRequestedDate())
                        .put("charges", req.getCharges()));

        return post("/transfer/sepa", json);
    }

    public JSONObject executeSwift(SwiftTransferRequest req) throws Exception {

        JSONObject body = new JSONObject();

        body.put("currency", req.getCurrency());
        body.put("amount", req.getAmount());

        body.put("beneficiary", new JSONObject()
                .put("name", req.getBeneficiaryName())
                .put("address", req.getBeneficiaryAddress())
                .put("account", req.getBeneficiaryAccount())
        );

        body.put("beneficiaryBank", new JSONObject()
                .put("name", req.getBankName())
                .put("swiftCode", req.getSwiftCode())
                .put("country", req.getCountry())
        );

        body.put("fees", new JSONObject()
                .put("chargingModel", req.getCharges())
        );

        body.put("correspondentBank", new JSONObject()
                .put("required", req.getCorrespondentBank())
        );

        return post("/transfer/swift", body);
    }

    /* ---------- HTTP helper ---------- */
    private JSONObject post(String path, JSONObject body) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body());
    }


}