package Telegram_Integration;

import Wheels.IssueBikeUI;

public class TelegramUser{
    private String chatId;
    private String username;

    private int etapa = 0;

    private String pcode;
    private Integer telephone;
    private Integer chosenBike;
    private Integer nDays;
    private IssueBikeUI ui;

    public TelegramUser(String username, String chatId) {
        this.chatId = chatId;
        this.username = username;
        this.ui = new IssueBikeUI();

    }

    public String getChatId() {
        return chatId;
    }

    public String[] handleMessage(String Message) {

        if(etapa == 0){
            return welcomeMessage();
        }

        if(chosenBike == null){
            return inputBike(Message);
        }

        if(nDays == null){
            return inputnDays(Message);
        }

        if(telephone == null){
            return inputTelephone(Message);
        }

        if(pcode == null){
            return inputPostcode(Message);
        }

        if(etapa == 2){
            return FinalizarPedido(Message);
        }

        return sendErrorMessage();
    }

    private String[] welcomeMessage() {
        etapa++;
        String message = "Olá, seja bem vindo " + username + "para iniciarmos seu autoatendimento por favor digite o numero da bike que deseja alugar";
        return new String[]{message};
    }
    private String[] inputBike(String Message){
        try {
            chosenBike = Integer.parseInt(Message);
            System.out.println(ui.getBikeDetails(chosenBike));
            if(ui.getBikeDetails(chosenBike) == null) {
                chosenBike = null;
                return new String[]{ "Não existe uma bike com este numero", "digite o numero da bike que deseja alugar" };
            }

            return new String[]{ ui.getBikeDetails(chosenBike), "Agora me diga por quantos dias deseja alugar"};

        }catch (Exception e ){
            e.printStackTrace();
            return new String[]{"Perdão, não consegui compreender. Digite o numero da Bike que deseja alugar"};
        }
    }
    private String[] inputnDays(String Message){
        try {
            nDays = Integer.parseInt(Message);
            return new String[]{
                    ui.calculateCost(nDays) ,
                    "Agora vamos confirmar algumas informações pessoais",
                    "digite o seu numero de telefone"
            };
        }catch (Exception e ){
            e.printStackTrace();
            return new String[]{"Perdão, não consegui compreender. Digite por quantos dias deseja alugar a bike "};
        }
    }
    private String[] inputTelephone(String Message){
        try {
            telephone = Integer.parseInt(Message);
            return new String[]{
                    "Por fim, me informe o seu codigo postal"
            };
        }catch (Exception e ){
            e.printStackTrace();
            return new String[]{
                    "Perdão, não consegui compreender",
                    "Digite o seu telefone (somente numeros) "
            };
        }
    }
    private String[] inputPostcode(String Message){
        pcode = Message;
        etapa++;
        return new String[]{
                "Confirme as informações do seu aluguel",
                ui.getBikeDetails(chosenBike),
                ui.calculateCost(nDays),
                "NOME : " + username + "\nTELEFONE : " + telephone + "\nCODIGO POSTAL : " + pcode,
                "Deseja confirmar o aluguel (digite S ou N)"
        };
    }
    private String[] FinalizarPedido(String message) {
        message = message.toLowerCase();
        if (message.equals("s")){
            ui.createCustomer(username, pcode, telephone);
            clearUserInteractions();
            return new String[]{
                    "Obrigado por alugar com o sistema Wheels",
                    ui.calculateTotalPayment()
            };
        }

        if (message.equals("n")){
            clearUserInteractions();
            return new String[]{
                    "Retornando ao estado inicial ...",
                    welcomeMessage()[0]
            };
        }

        return new String[]{
                "Não consegui compreender",
                "Digite 'S' para confirmar o Aluguel.\nDigite 'N' para cancelar e retornar ao inicio"
        };
    }

    private String[] sendErrorMessage() {
        return new String[]{
                "houve um erro. tente novamente mais tarde"
        };
    }

    private void clearUserInteractions(){
        pcode = null;
        telephone = null;
        chosenBike = null;
        nDays = null;
        etapa = 0;
    }
}
