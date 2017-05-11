$(function () {
	
	$('#nav').tree({
		url : 'nav.php',
		lines : true,
		onLoadSuccess : function (node, data) {
			if (data) {
				$(data).each(function (index, value) {
					if (this.state == 'closed') {
						$('#nav').tree('expandAll');
					}
				});
			}
		},
		onClick : function (node) {
			if (node.url) {
				if ($('#tt').tabs('exists', node.text)) {
					$('#tt').tabs('select', node.text);
				} else {
					$('#tt').tabs('add', {
						title : node.text,
						iconCls : node.iconCls,
						closable : true,
						href : node.url,
					});
				}
			}
		}
	});
	
	$('#tabs').tabs({
		fit : true,
		border : false,
	});
	
});