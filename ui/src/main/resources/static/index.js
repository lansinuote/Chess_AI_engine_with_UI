var board;
var game;

var makeBestMove = function () {
    $.get('get',{
        'fen':game.fen(),
        'depth':$('#depth').val(),
        'width':$('#width').val(),
        'pool_size':$('#pool_size').val(),
        'leaf_depth':$('#leaf_depth').val(),
    },function(json){
        console.log({from:json.from.toLowerCase(),to:json.to.toLowerCase(),promotion:'q'});
        game.move({from:json.from.toLowerCase(),to:json.to.toLowerCase(),promotion:'q'});
        board.position(game.fen());
        renderMoveHistory(game.history());
        if (game.game_over()) {
            consoe.log('Game over');
            //alert('Game over');
        }
    });
};


var renderMoveHistory = function (moves) {
    $('#fen').val(game.fen());
    var historyElement = $('#move-history').empty();
    historyElement.empty();
    for (var i = 0; i < moves.length; i = i + 2) {
        historyElement.append('<span>' + moves[i] + ' ' + ( moves[i + 1] ? moves[i + 1] : ' ') + '</span><br>')
    }
    historyElement.scrollTop(historyElement[0].scrollHeight);

};

var onDrop = function (source, target) {
    var move = game.move({
        from: source,
        to: target,
        promotion: 'q'
    });

    removeGreySquares();
    if (move === null) {
        return 'snapback';
    }

    renderMoveHistory(game.history());
    window.setTimeout(makeBestMove, 250);
};

var onSnapEnd = function () {
    board.position(game.fen());
};

var onMouseoverSquare = function(square, piece) {
    var moves = game.moves({
        square: square,
        verbose: true
    });

    if (moves.length === 0) return;

    greySquare(square);

    for (var i = 0; i < moves.length; i++) {
        greySquare(moves[i].to);
    }
};

var onMouseoutSquare = function(square, piece) {
    removeGreySquares();
};

var removeGreySquares = function() {
    $('#board .square-55d63').css('background', '');
};

var greySquare = function(square) {
    var squareEl = $('#board .square-' + square);

    var background = '#a9a9a9';
    if (squareEl.hasClass('black-3c85d') === true) {
        background = '#696969';
    }

    squareEl.css('background', background);
};

$('#submit').click(function(){
    var fen = $('#fen').val();
    console.log(fen);

    game = new Chess(fen);
    var cfg = {
        draggable: true,
        position: fen,
        onDrop: onDrop,
        onMouseoutSquare: onMouseoutSquare,
        onMouseoverSquare: onMouseoverSquare,
        onSnapEnd: onSnapEnd
    };
    board = ChessBoard('board', cfg);

    makeBestMove();
})