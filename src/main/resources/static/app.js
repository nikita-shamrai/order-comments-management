function editComment(orderID, commentId) {
    let divBlockTag = $('#comment_' + orderID + '_' + commentId);
    let spanTag = $('#comment_' + orderID + '_' + commentId + ' span');
    let commentText = spanTag.text();
   divBlockTag.html(
       '<form onsubmit="saveEditedComment(' + orderID + ', ' + commentId + ')">' +
        '<label for="edit_comment_'+ orderID + '_' + commentId + '"></label>'+
        '<input type="text" name="edit_comment" id="edit_comment_'+ orderID + '_' + commentId + '" value="' + commentText + '" required>' +
        '<input type="submit" value="Submit">' +
        '</form>');
    spanTag.text('');
    $('#editComment_' + orderID + '_' + commentId).show();
    $('#edit_button_' + orderID + '_' + commentId).hide();
    $('#delete_button_' + orderID + '_' + commentId).hide();
}

function saveEditedComment(orderId, commentId) {
    let commentText = $('#comment_' + orderId + '_' + commentId + ' input').val();
    $.post('/editComment', {orderId: orderId, commentId: commentId, newComment: commentText},
        function () {}).fail(function () {
        console.log("error");
    });
}

function deleteComment(orderId, commentId) {
    $.post('/deleteComment', {orderId: orderId, commentId: commentId},
        function () {
            let scrollPosition = window.scrollY;
            location.reload()
            window.scrollTo(0, scrollPosition);
        }).fail(function () {
        console.log("error");
    });
}

function openAddCommentForm(orderID) {
    $('#addComment_' + orderID).show();
}

function cancelAddComment(orderID) {
    $('#addComment_' + orderID + ' #comment').val('');
    $('#addComment_' + orderID).hide();
}