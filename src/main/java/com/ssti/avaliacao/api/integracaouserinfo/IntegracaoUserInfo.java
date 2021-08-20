package com.ssti.avaliacao.api.integracaouserinfo;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IntegracaoUserInfo {

    public static ResultadoUsersCpf verificaCpf(String cpf) throws Exception {

        ResultadoUsersCpf resultado = null;
        try {
            String url = "https://user-info.herokuapp.com/users/" + cpf;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
                return null;
            }

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String jsonEmString = "";
            jsonEmString = IntegracaoUserInfo.converteRespostaEmJson(resposta);

            resultado = converterJsonStringEmObjeto(jsonEmString);

            conn.disconnect();

        } catch (IOException ex) {
            System.out.println("Erro ao executar operação. " + ex.getMessage());
        }
        return resultado;
    }

    private static String converteRespostaEmJson(BufferedReader buffereReader) throws IOException {
        String resposta, jsonEmString = "";
        while ((resposta = buffereReader.readLine()) != null) {
            jsonEmString += resposta;
        }
        return jsonEmString;
    }

    private static ResultadoUsersCpf converterJsonStringEmObjeto(String json) throws IOException {

        ResultadoUsersCpf objeto = new Gson().fromJson(json, ResultadoUsersCpf.class);
        return objeto;
    }

    public class ResultadoUsersCpf {

        private String status;

        public ResultadoUsersCpf(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ResultadoUsersCpf{" + "status=" + status + '}';
        }
    }

    public enum ResultadoUsersCpfStatusEnum {

        ABLE_TO_VOTE, UNABLE_TO_VOTE;
    }

}
