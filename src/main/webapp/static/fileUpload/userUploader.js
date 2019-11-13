
    jQuery(function() {

    	uploader = new Array();//创建 uploader数组
    	// 优化retina, 在retina下这个值是2
        var ratio = window.devicePixelRatio || 1,
        // 缩略图大小
        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio,
        supportTransition = (function(){
            var s = document.createElement('p').style,
            r = 'transition' in s ||
                  'WebkitTransition' in s ||
                  'MozTransition' in s ||
                  'msTransition' in s ||
                  'OTransition' in s;
	        s = null;
	        return r;
        })();
     	// 所有文件的进度信息，key为file id
        var percentages = {};
        var state = 'pedding';

    	//可行性判断
    	if ( !WebUploader.Uploader.support() ) {
            alert( 'Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
            throw new Error( 'WebUploader does not support the browser you are using.' );
        }

    	//循环页面中每个上传域
    	$('.uploder-container').each(function(index){

    		// 添加的文件数量
            var fileCount = 0;
            // 添加的文件总大小
            var fileSize = 0;
    		var filePicker=$(this).find('.filePicker');//上传按钮实例
    		var queueList=$(this).find('.queueList');//拖拽容器实例
    		var jxfilePicker=$(this).find('.jxfilePicker');//继续添加按钮实例
    		var placeholder=$(this).find('.placeholder');//按钮与虚线框实例
    		var statusBar=$(this).find('.statusBar');//再次添加按钮容器实例
    		var info =statusBar.find('.info');//提示信息容器实例
    		var userId = $(this).find('#userId');
    		// 图片容器
    		var queue = $('<ul class="filelist"></ul>').appendTo( queueList);
    		//初始化上传实例
            uploader[index] = WebUploader.create({
                pick: {
                    id: filePicker,
                    label: '上传'
                },
                dnd: queueList,

                //这里可以根据 index 或者其他，使用变量形式
                accept: {
                    title: 'files',
                    extensions: 'gif,jpg,jpeg,bmp,png,doc,docx,pdf,tif',
                    mimeTypes: 'image/*'
                },

                // swf文件路径
                swf: '../Uploader.swf',

                disableGlobalDnd: true,//禁用浏览器的拖拽功能，否则图片会被浏览器打开

                chunked: false,//是否分片处理大文件的上传

                // server: 'http://webuploader.duapp.com/server/fileupload.php',
                server: 'https://ehr.wxchina.com/ehr/fielUpload/morefileUpload',
               // server: 'http://127.0.0.1:8080/a/ehr/fielUpload/morefileUpload',

                fileNumLimit: 10,//一次最多上传多少张照片

                fileSizeLimit: 5000 * 1024 * 1024,    // 200 M

                fileSingleSizeLimit: 500 * 1024 * 1024,    // 50 M

                auto :true,

                formData: {
                	token:index,
                    userId:$("#userId").val()//可以在这里附加控件编号，从而区分是哪个控件上传的

                }
            });


            // 添加“添加文件”的按钮
            uploader[index].addButton({
                id: jxfilePicker,
                label: '继续添加'
            });

            //当文件加入队列时触发	uploader[0].upload();
            uploader[index].onFileQueued = function( file ) {
    		    fileCount++;
                fileSize += file.size;
                if ( fileCount === 1 ) {
                	placeholder.addClass( 'element-invisible' );
                    statusBar.show();
                }
                addFile( file,uploader[index],queue,index);
                setState( 'ready' ,uploader[index],placeholder,queue,statusBar,jxfilePicker);
                updateStatus('ready',info,fileCount,fileSize);

            };

            //当所有文件上传结束时触发	uploader[0].upload();
            uploader[index].onUploadFinished = function( file ) {
            	//alert("所有档案文件都已经上传成功");
                uploader[index].reset();//重置队列
            };



            //当文件被移除队列后触发。
            uploader[index].onFileDequeued = function( file ) {

                $.ajax({
                   url:'https://ehr.wxchina.com/ehr/fielUpload/deleteAll?ids='+file.id,
                  // url:'http://127.0.0.1:8080/a/ehr/fielUpload/deleteAll?ids='+file.id,
                	async:false,
                    type:'post',
                    dataType:'text',
                    success:function(result){
                    	if (result == '0') {
                    		alert("删除失败，未有权限");
                    	}
                    	else {
                    		//alert("删除成功");
                    		fileCount--;
                    		fileSize -= file.size;
                    		removeFile( file ,index);
                    		if ( !fileCount ) {
                    			setState( 'pedding',uploader[index],placeholder,queue,statusBar,jxfilePicker);
                    			updateStatus('pedding',info,fileCount,fileSize);
                    		}else{
                    			updateStatus('ready',info,fileCount,fileSize);
                    		}
                    	}
                    },
                    error: function(result) {
                    	alert("删除失败，未有权限");
                    }
                });


            };

            //上传成功后回调
            uploader[index].on('uploadSuccess',function(file,reponse){

                console.log(reponse);

                if(reponse.status == '-1'){
                    alert("有文件上传出错，请重传");
                    fileCount--;
                    fileSize -= file.size;
                    removeFile( file ,index);
                    if ( !fileCount ) {
                        setState( 'pedding',uploader[index],placeholder,queue,statusBar,jxfilePicker);
                        updateStatus('pedding',info,fileCount,fileSize);
                    }else{
                        updateStatus('ready',info,fileCount,fileSize);
                    }
                }else{
                    $('#'+file.id).attr('id',reponse.id);
                    file.id=reponse.id;
                    file.url=reponse.url;
                    file.type=reponse.type;

                    // uploader[index].reset();//重置队列
                }


            });



            //可以在这里附加额外上传数据
            uploader[index].on('uploadBeforeSend',function(object,data,header) {
            	/*var tt=$("input[name='id']").val();
            	data=$.extend(data,{
            		modelid:tt
            		});*/
            });

            //加载的时候
            uploader[index]	.on('ready',function(){
                $.ajax({
                    url:'https://ehr.wxchina.com/ehr/fielUpload/backShow?token='+index + "&userId=" + $("#userId").val(),
                   // url:' http://127.0.0.1:8080/a/ehr/fielUpload/backShow?token='+index + "&userId=" + $("#userId").val(),
                    type:'GET',
                    async:false,
                    success:function(files){


                        for(var i = 0; i < files.length; i++){
                            console.log(files);
                            var obj ={};
                            statusMap = {};
                            fileCount++;
                            fileSize += Number(files[i].size);
                            if ( fileCount === 1 ) {
                                placeholder.addClass( 'element-invisible' );
                                statusBar.show();
                            }
                            obj.id=files[i].id;
                            obj.name=files[i].name;
                            obj.type=files[i].type;
                            obj.size=files[i].size;
                            obj.ret='https://kite.wxchina.com/file/'+files[i].url;
                            obj.source=this;
                            obj.flog=true;
                            obj.url=files[i].url;
                            obj.status = 'complete',
                                obj.getStatus = function(){
                                    return '';
                                }
                            obj.version = WebUploader.Base.version;
                            obj.statusText = '';
                            obj.setStatus = function(){
                                var prevStatus = statusMap[this.id];
                                typeof text !== 'undefined' && (this.statusText = text);
                                if(status !== prevStatus){
                                    statusMap[this.id] = status;
                                    //文件状态设置为已完成
                                    uploader[index].trigger('statuschage',status,prevStatus);
                                }
                            }
                            addFile( obj,uploader[index],queue,index);
                            setState( 'ready' ,uploader[index],placeholder,queue,statusBar,jxfilePicker);
                            updateStatus('ready',info,fileCount,fileSize);
                        }
                    }
                });
            });


            /**
        	验证文件格式以及文件大小
        	*/
        	uploader[index].on("error", function (type) {
        		//console.log("-------------------error--------")
        		if (type == "Q_TYPE_DENIED") {
        			 alert("请上传JPG、PNG、GIF、BMP格式文件");
        		} else if (type == "Q_EXCEED_SIZE_LIMIT") {
        			 alert("文件大小不能超过2M");
        		}else {
        			 alert("上传出错！请检查后重新上传！错误代码:"+type);
        		}
        	});


    	});


        // 当有文件添加进来时执行，负责view的创建
        function addFile( file,now_uploader,queue,index) {
            var $li = $( '<li id="' + file.id + '">' +
                    '<p class="title">' + file.name + '</p>' +
                    '<p class="imgWrap"></p>'+
                    '<p class="progress"><span></span></p>' +
                    '</li>' ),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                   '<span class="rotateRight">向右旋转</span>' +
                    '<span class="rotateLeft">向左旋转</span>' +
                    '</div>').appendTo( $li ),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find( 'p.imgWrap' ),
                $info = $('<p class="error"></p>');

            $wrap.text( '预览中' );
            if(file.flog == true){
            	var img = $('<img src="'+file.ret+'" width="110px" height="110px">');
                $wrap.empty().append( img );
            }else{
            	now_uploader.makeThumb( file, function( error, src ) {
                    if ( error ) {
                        $wrap.text( '不能预览' );
                        return;
                    }

                    var img = $('<img src="'+src+'" >');
                    $wrap.empty().append( img );
                }, thumbnailWidth, thumbnailHeight );
            }
            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;


            /*file.on('statuschange', function( cur, prev ) {
                if ( prev === 'progress' ) {
                    $prgress.hide().width(0);
                } else if ( prev === 'queued' ) {
                    $li.off( 'mouseenter mouseleave' );
                    $btns.remove();
                }

                // 成功
                if ( cur === 'error' || cur === 'invalid' ) {
                    console.log( file.statusText );
                    showError( file.statusText );
                    percentages[ file.id ][ 1 ] = 1;
                } else if ( cur === 'interrupt' ) {
                    showError( 'interrupt' );
                } else if ( cur === 'queued' ) {
                    percentages[ file.id ][ 1 ] = 0;
                } else if ( cur === 'progress' ) {
                    $info.remove();
                    $prgress.css('display', 'block');
                } else if ( cur === 'complete' ) {
                    $li.append( '<span class="success"></span>' );
                }

                $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
            });*/


            $li.on( 'mouseenter', function() {
                $btns.stop().animate({height: 30});
            });

            $li.on( 'mouseleave', function() {
                $btns.stop().animate({height: 0});
            });

            $wrap.on( 'click',function() {

                reviewChoose(file.url,file.name,file.type);
            });

            $btns.on( 'click', 'span', function() {
                var index = $(this).index(),
                    deg;

                switch ( index ) {
                    case 0:
                    	now_uploader.removeFile( file ,index);
                        return;

                    case 1:
                        file.rotation += 90;
                        break;

                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if ( supportTransition ) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                }

            });

            $li.appendTo($(".filelist").eq(index));
        }

        // 负责view的销毁
        function removeFile( file ,index) {


            var $li = $(".uploder-container").eq(index).find("#"+file.id);
            delete percentages[ file.id ];
            $li.off().find('.file-panel').off().end().remove();
        }

        function reviewChoose(url,fileName,fileType) {

            url = url.replace(/\//g,"_!!!_");

            var path = "https://ehr.wxchina.com/fileService/kitefile/pdfjs/web/viewer.html?file=https://ehr.wxchina.com/fileService/kitefile/api/inter/fastdfs/reviewFile/"+url + "/"+fileType+"/"+encodeURIComponent(fileName)+"/";

            window.open(path,"","titlebar=no,location=no,status=no");

        }



        function setState( val, now_uploader,placeHolder,queue,statusBar,jxfilePicker) {
            switch ( val ) {
                case 'pedding':
                    placeHolder.removeClass( 'element-invisible' );
                    queue.parent().removeClass('filled');
                    queue.hide();
                    statusBar.addClass( 'element-invisible' );
                    now_uploader.refresh();
                    break;

                case 'ready':
                    placeHolder.addClass( 'element-invisible' );
                    jxfilePicker.removeClass( 'element-invisible');
                    queue.parent().addClass('filled');
                    queue.show();
                    statusBar.removeClass('element-invisible');
                    now_uploader.refresh();
                    break;
            }
        }

        function updateStatus(val,info,f_count,f_size) {
            var text = '';
            if ( val === 'ready' ) {
                text = '已上传' + f_count + '个文件';
                    //+ '共' + WebUploader.formatSize( f_size ) + '。';
            }
            info.html( text );
        }
    });
