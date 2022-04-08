// função para usar o json
function utilize(cep){
    $.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

        //Atualiza os campos com os valores da consulta do json.
    
        /* valido dizer que os nomes do .val devem estar extamente assim 
        para que as pesquisas do jason sejam realizdas e limpas
        exemplo: caso no estado seja colocado 
        .val(dados.estado)
        Isso não funcionaria pois uf é a maneira correta de se referir a um estado em documentos
        */
       
        $("#rua").val(dados.logradouro);
        $("#bairro").val(dados.bairro);
        $("#cidade").val(dados.localidade);
        $("#estado").val(dados.uf);                   
    
    });
}
