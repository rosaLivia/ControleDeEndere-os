# DiscoverPlaces - Programação Mobile

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Trabalho 2 - Avaliação 2 - Turma P01</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Trabalho 2 - Avaliação 2 - Turma P01</h1>
        <h2>Programação para Dispositivos Móveis</h2>
        <p>INDIVIDUAL ou DUPLA</p>
        <p>Prof. Ana Karina Dourado Salina de Oliveira</p>
        <p>9 de maio de 2024</p>
    </header>
    <main>
        <section>
            <h2>1. Introdução</h2>
            <p>Este documento descreverá quais funcionalidades o projeto deve implementar. Não será permitido que nenhum acadêmico desenvolva qualquer outro aplicativo diferente do listado a seguir. As descrições apresentadas devem ser consideradas como requisitos mínimos. É fortemente recomendado que os estudantes adicionem novos requisitos às aplicações e construam interfaces de alta usabilidade. Cópias de código entre os grupos ou Internet será tratada com nota ZERO para todos os envolvidos.</p>
        </section>
        <nav>
            <ul>
                <li><a href="controle_enderecos.html">Controle de Endereços</a></li>
                <li><a href="entrega.html">O que e como deve ser entregue?</a></li>
            </ul>
        </nav>
    </main>
</body>
</html>



<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Controle de Endereços</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>Controle de Endereços</h1>
    </header>
    <main>
        <section>
            <h2>2. Controle de Endereços</h2>
            <p>Este projeto consiste em desenvolver uma App Android de Controle de Endereços. O projeto deverá ser desenvolvido com o Banco de Dados Room.</p>
            <h3>Banco de Dados: ControleDeEnderecos</h3>
            <p><strong>Tabela Usuario</strong><br>
            int usuarioId;<br>
            String nome;<br>
            String email;<br>
            String senha;</p>
            <p><strong>Tabela Cidade</strong><br>
            int cidadeId;<br>
            String cidade;<br>
            String estado;</p>
            <p><strong>Tabela Endereco</strong><br>
            int enderecoId;<br>
            String descricao; (exemplos: FACOM, HU, Praça Ari Coelho, etc)<br>
            double latitude;<br>
            double longitude;<br>
            int cidadeId; (chave estrangeira)</p>
            <h3>Requisitos Gerais</h3>
            <ul>
                <li>Permitir o registro de Usuarios;</li>
                <li>Permitir o Login de Usuários cadastrados;</li>
                <li>Permitir o registro de Cidades após o Login do Usuario;</li>
                <li>Permitir o registro de Enderecos após o Login do Usuario;</li>
                <li>Permitir operações como incluir, excluir e alterar dados nestas tabelas;</li>
                <li>Na operação de inserção de Enderecos, deverá listar o nome de todas as cidades já cadastradas em um spinner ou em um list view;</li>
                <li>Permitir abrir o endereco selecionado em um Mapa utilizando o Google Maps;</li>
            </ul>
            <h3>Funcionalidades Detalhadas</h3>
            <ul>
                <li><strong>Permitir o registro de Usuario:</strong> A aplicação deve ser capaz de permitir ao usuário que ele adicione ou atualize informações de Usuarios.</li>
                <li><strong>Permitir o Login de Usuario:</strong> Permitir que o Usuario faça o login digitando email e senha. Nesta função deverá fazer uma consulta (@Query) para checar se o email e senha coincidem. Em caso negativo, não deverá deixar o Usuario acessar as outras telas de cadastro de Cidade e Enderecos. Se o login estiver correto, poderá direcionar diretamente para a tela que mostra todas as Cidades cadastradas em um List View.</li>
                <li><strong>Permitir o registro de Cidades:</strong> A aplicação deve ser capaz de permitir ao usuário que ele adicione ou atualize informações das Cidades após o login do Usuário.</li>
                <li><strong>Permitir o registro de Enderecos:</strong> Sempre que um novo Endereco for adicionado deve-se buscar a Cidade ligada ao Endereco. Se não existir a Cidade, o sistema deve permitir a inserção de uma nova Cidade. Nesta tela, o usuário poderá solicitar mostrar o endereço no mapa. O mapa poderá estar na mesma atividade ou em outra atividade.</li>
                <li><strong>Permitir operações como incluir, excluir e alterar dados nestas tabelas:</strong> O aplicativo deve permitir inserir, alterar ou excluir dados em todas as tabelas.</li>
                <li><strong>Requisito:</strong> Sempre que for excluir ou atualizar uma Cidade, verificar se não tem Enderecos ligados aquele Cidade. Se tiver, deverá ou excluir ou atualizar os Enderecos ligados aquele Cidade ou então não deve permitir excluir a Cidade.</li>
                <li><strong>Permitir listar os Usuarios cadastrados:</strong> Os Usuarios adicionados deverão aparecer em um list View que deverá mostrar o nome do Usuario, o email e o telefone.</li>
                <li><strong>Permitir listar os Cidades cadastradas:</strong> As Cidades adicionados deverão aparecer em um list View que deverá mostrar o nome do Cidade e o estado.</li>
                <li><strong>Permitir listar os Enderecos cadastrados:</strong> Os Enderecos adicionados deverão aparecer em um list View que deverá mostrar a descrição do Endereco e a Cidade do Endereco.</li>
            </ul>
        </section>
        <nav>
            <ul>
                <li><a href="introducao.html">Introdução</a></li>
                <li><a href="entrega.html">O que e como deve ser entregue?</a></li>
            </ul>
        </nav>
    </main>
</body>
</html>







<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>O que e como deve ser entregue?</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <h1>O que e como deve ser entregue?</h1>
    </header>
    <main>
        <section>
            <h2>3. O que e como deve ser entregue?</h2>
            <p>Um link no Moodle estará disponível para entrega do trabalho até o dia 21/05/24 às 23:00 hrs. Enviar:</p>
            <ol>
                <li>O nome do(s) aluno(s)</li>
                <li>Um link para o drive onde está armazenado o projeto completo do Android</li>
                <li>Um link para o diretório de apresentação do trabalho em execução (máx 10 minutos), onde deverá ser apresentado o trabalho em execução e as principais funcionalidades implementadas no código.</li>
            </ol>
        </section>
        <nav>
            <ul>
                <li><a href="introducao.html">Introdução</a></li>
                <li><a href="controle_enderecos.html">Controle de Endereços</a></li>
            </ul>
        </nav>
    </main>
</body>
</html>

