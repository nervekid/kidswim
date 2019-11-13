function collectionMenu(url,menuUrl,title,menuId) {
    var checked = $("#collectionId").attr("checked");
    var flag = '';
    if("checked"==checked){
        flag='add';
    }else{
        flag='delete';
    }
    $.post(url,
        {
            flag:flag,
            menuUrl:menuUrl,
            title:title,
            menuId:menuId
        },
        function(data,status){
            if(status){
            	top.layer.alert(data, {icon: 0, title:'提示'});
            }
        });
}
function keyDownEnter(e){
    var ev= window.event||e;
    if (ev.keyCode == 13) {
        search();
    }
}