// função para pesquisa
$(document).ready(function() {

    
    //Quando tiramos o foco do Input do CEP, assim não temos que adicionar um botão para pesquisa
    $("#cep").blur(function() {


        //Nova variável "cep" somente com dígitos.
        var cep = $(this).val().replace(/\D/g, '');

        //Verifica se campo cep possui valor informado.
       
        if (cep != "") {

            //validar cep

            /* 

            um link onde eu pude tomar base para fazer a validação do cep

            https://sdevlab.wordpress.com/2010/12/20/javascript-validacao-de-cep/#:~:text=Para%20isto%20este%20c%C3%B3digo%20usa,o%20cep%20de%20uma%20pessoa.&text=A%20fun%C3%A7%C3%A3o%20retorna%20true%20caso,ou%20false%20caso%20n%C3%A3o%20esteja.
            
            */
            var cepVal = /^[0-9]{8}$/;

            //Valida o formato do CEP.
            if(cepVal.test(cep)) {
                //consumindo o json
                utilize(cep);              
            } 
           
        } 
        else {
            //Valor invalido
            limpa_formulário_cep();
        }
    });

});
