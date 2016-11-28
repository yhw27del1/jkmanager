<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/common/taglib.jsp"%>
<%@ include file="/common/import.jsp"%>
<html>
	<head>
		<title>新增(修改)会员</title>
		<script type="text/javascript" src="/wisdoorStatic/js/autoheight.js"></script>
		<script type="text/javascript"
			src="/wisdoorStatic/js/validate/jquery.validate.js"></script>
		<link rel="stylesheet"
			href="/wisdoorStatic/js/validate/validateself-skin1.css"
			type="text/css" />
		<link rel="stylesheet" href="/wisdoorStatic/css/member.css"
			type="text/css" />
		<script type="text/javascript"
			src="/wisdoorStatic/js/jquery.uploadify-v2.1.0/swfobject.js"></script>
		<script type="text/javascript"
			src="/wisdoorStatic/js/jquery.uploadify-v2.1.0/jquery.uploadify.v2.1.0.min.js"></script> 
		<link href="/wisdoorStatic/js/lhgdialog/_doc/common.css" type="text/css" rel="stylesheet"/> 
	    <script type="text/javascript" src="/wisdoorStatic/js/lhgdialog/lhgdialog/lhgdialog.min.js"></script>
		<script type="text/javascript">
		
		function openDocument(url) {
		window.open(url,'',"height='100',width='400',top='0',left='0',toolbar='no',menubar='no',scrollbars='yes', resizable='yes',location='no', status='no'");
	    }
		
		/**检验身份证号码是否合法
		15位身份证号码组成：
        ddddddyymmddxxs共15位，其中：
        dddddd为6位的地方代码，根据这6位可以获得该身份证号所在地。
        yy为2位的年份代码，是身份证持有人的出身年份。
        mm为2位的月份代码，是身份证持有人的出身月份。
        dd为2位的日期代码，是身份证持有人的出身日。
                      这6位在一起组成了身份证持有人的出生日期。
        xx为2位的顺序码，这个是随机数。
        s为1位的性别代码，奇数代表男性，偶数代表女性。		
		18位身份证号码组成：ddddddyyyymmddxxsp共18位，其中：
                      其他部分都和15位的相同。年份代码由原来的2位升级到4位。最后一位为校验位。
                      校验规则是：
                        （1）十七位数字本体码加权求和公式
           S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
           Ai:表示第i位置上的身份证号码数字值
           Wi:表示第i位置上的加权因子
           Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
                       （2）计算模
           Y = mod(S, 11)
                       （3）通过模得到对应的校验码
           Y: 0 1 2 3 4 5 6 7 8 9 10
                             校验码: 1 0 X 9 8 7 6 5 4 3 2
                            也就是说，如果得到余数为1则最后的校验位p应该为对应的0.如果校验位不是，则该身份证号码不正确。
		**/
		function isValidIdCard(idCard){
		  var ret=false;
     	   var w=[7 ,9,10,5,8,4,2, 1,6, 3, 7, 9 ,10, 5 ,8 ,4, 2];     	   
		   if(idCard.length == 18){
		     //身份证号码长度必须为18，只要校验位正确就算合法
		      var crc=idCard.substring(17);
		      var a =new Array();
		      var sum=0;
		      for(var i=0;i<17;i++){
		        a.push(idCard.substring(i,i+1));
		        sum+=parseInt(a[i],10)*parseInt(w[i],10);
		        //alert(a[i]);
		      }
		      sum%=11;
		      var res="-1";
		      switch (sum){
		      case 0:{
		        res="1";
		        break;
		      }
		      case 1:{
		                   res="0";
		                   break;
		                   }
		      case 2:{
		                   res="X";
		                   break;
		                   }
		      case 3:{
		                   res="9";
		                   break;
		                   }
		      case 4:{
		                   res="8";
		                   break;
		                   }
		      case 5:{
		                   res="7";
		                   break;
		                   }
		      case 6:{
		                   res="6";
		                   break;
		                   } 
		       case 7:{
		                   res="5";
		                   break;
		                   } 
		       case 8:{
		                   res="4";
		                   break;
		                   } 
		        case 9:{
		                   res="3";
		                   break;
		                   }
		         case 10:{
		                   res="2";
		                   break;
		                   }
		      }
		      if(crc.toLowerCase()==res.toLowerCase()){
		         ret=true; 
		      }
		      //ret=true; 
		   }
		  return ret;	   
		}	
		
		/**
		*验证图片类型(jpg,jpeg,png,gif)
		*/
	  /*	function isValidImageType(fileName){
		    var temp=fileName.split(".");
		    var length=temp.length;
		    //alert("length= "+length+",name="+temp[length-1]);
		    var ret=false;
		    var typeName=temp[length-1];//fileName.substring(fileName.length-4);
		    //alert("typeName="+typeName);
		    typeName=typeName.toLowerCase();
		    //alert("typeName="+typeName);
		    if(""==fileName){
		    ret=true;
		    }	    
		    //alert("fileName="+fileName+",typeName="+typeName);
		    else if("jpg"==typeName  || "png"==typeName || "gif"==typeName || "jpeg"==typeName){
		       ret=true;
		    }
		    return ret;
		}*/
		
		/**
		*验证组织机构代码是否合法：组织机构代码为8位数字或者拉丁字母+“-”+1位校验码。
		*验证最后那位校验码是否与根据公式计算的结果相符。
		*编码规则请参看
		*http://wenku.baidu.com/view/d615800216fc700abb68fc35.html
		*/
		/* function isValidOrgCode(orgCode){
		    return ""==orgCode || orgCode.length==10;
		    //return true;
		    
		}*/
		
       /**
		*验证营业执照是否合法：营业执照长度须为15位数字，前14位为顺序码，
		*最后一位为根据GB/T 17710 1999(ISO 7064:1993)的混合系统校验位生成算法
		*计算得出。此方法即是根据此算法来验证最后一位校验位是否政正确。如果
		*最后一位校验位不正确，则认为此营业执照号不正确(不符合编码规则)。
		*以下说明来自于网络:
		*我国现行的营业执照上的注册号都是15位的，不存在13位的，从07年开始国
		*家进行了全面的注册号升级就全部都是15位的了，如果你看见的是13位的注
		*册号那肯定是假的。
		*15位数字的含义，代码结构工商注册号由14位数字本体码和1位数字校验码
		*组成，其中本体码从左至右依次为：6位首次登记机关码、8位顺序码。　　
　　  *   一、前六位代表的是工商行政管理机关的代码，国家工商行政管理总局用
        *           “100000”表示，省级、地市级、区县级登记机关代码分别使用6位行
        *             政区划代码表示。设立在经济技术开发区、高新技术开发区和保税区
        *             的工商行政管理机关（县级或县级以上）或者各类专业分局应由批准
        *             设立的上级机关统一赋予工商行政管理机关代码，并报国家工商行政
        *             管理总局信息化管理部门备案。
　　  *   二、顺序码是7-14位，顺序码指工商行政管理机关在其管辖范围内按照先
        *             后次序为申请登记注册的市场主体所分配的顺序号。为了便于管理和
        *              赋码，8位顺序码中的第1位（自左至右）采用以下分配规则：
　　  *　　          1）内资各类企业使用“0”、“1”、“2”、“3”；
　　  *　　          2）外资企业使用“4”、“5”；
　　  *　　          3）个体工商户使用“6”、“7”、“8”、“9”。　　
　　  *   顺序码是系统根据企业性质情况自动生成的。　　
　　  *三、校验码是最后一位，校验码用于检验本体码的正确性
		*/
		/*function isValidBusCode(busCode){
		return true;
       
		}*/
		
		/**
		*验证国税税务登记号是否合法:税务登记证是6位区域代码+组织机构代码
		*/
	/* 	function isValidTaxCode(taxCode){
		   var ret=true;
		   if(isValidOrgCode($("#memberBase.eOrgCode").val())){
		     
		   }
		   return ret;
		}
		 */
		
		/**
		*验证固定电话号码。固定电话号码格式为：区号-号码
		*/
		function isValidPhone(phone){
		  var ret=false;
		  if(""==phone || (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?/.test(phone))){
		     ret=true;
		  }
		  return ret;
		}
		
		
		function isValidMobile(mobile){
		  //alert(mobile);
		  var ret=false;
		  if(""==mobile || /^1\d{10}$/.test(mobile)){
		    ret=true;
		  }
		  return ret;
		}
		
		/**
		*验证邮政编码是否有效。大陆的邮政编码为6位数字。
		*/
		function isValidPostCode(postCode){
		  return ""==postCode || postCode.length==6;
		}
		
		$(function() {
		      //alert("${id},"+(""!="${id}"));    
		      /*if(null!="${id}"){
		         $("#memberTypeId").hide();
		      }*/
		      
		      changeCategory(true,"${memberBase.category}");	
		      		       	       
			  jQuery.validator.addMethod("isValidIdCard", function(value, element, param) { 
			       return isValidIdCard(value);
			   }, "身份证号码不正确，身份证号码必须为18位。");
              
              jQuery.validator.addMethod("isValidPhone",function(value,element,param){
                 return isValidPhone(value);
              });
              
              jQuery.validator.addMethod("isValidMobile",function(value,element,param){
                 return isValidMobile(value);
              });
                   
              jQuery.validator.addMethod("checkData",function(value,element,param){
                   return checkData(value);
              },"此身份证号码已使用");
              
       
		       $("#memberbaseform").validate({
		                rules: {
		                  "memberTypeId":{required:true},
		                  "memberBase.province":{ required:true},  
		                  "memberBase.city":{ required:true},  
// 		                  "memberBase.eName":{ required:true},  
// 		                  "industryId":{required:true},
// 		                  "companyPropertyName":{ required:true},
// 		                  "memberBase.eAddress":{ required:true},
// 		                  "memberBase.eOrgCode":{ required:true,isValidOrgCode:true},
// 		                  "orgCertificate":{required:false,isValidImageType:true,checkImgSize:true},   
// 		                  "memberBase.eBusCode":{ required:true,isValidBusCode:true},
// 		                  "busLicense":{required:false,isValidImageType:true,checkImgSize:true},
// 		                  "memberBase.eTaxCode":{ required:false},
// 		                  "taxRegCertificate":{isValidImageType:true,checkImgSize:true},
// 		                  "memberBase.eNature":{ required:true},	                  
// 		                  "memberBase.eMobile":{required:false,isValidMobile:true},
// 		                  "memberBase.eContact":{ required:false},
// 		                  "memberBase.eContactPhone":{ required:false},
// 		                  "banklibrary_id":{ required:true}, 
// 		                  "memberBase.bank":{ required:true}, 
		                  "memberBase.code":{ required:true},
		                  "memberBase.pName":{required:true},
		                  "memberBase.pSex":{required:true},
		                  "memberBase.pPhone":{required:false},
		                  "memberBase.pAddress":{required:true},
		                  "memberBase.pBirthday":{required:false},
                          "memberBase.email":{required:true},
		                  "memberBase.idCardNo":{required:false,isValidIdCard:true}, 
		                 
		                  "memberBase.pMobile":{required:true,isValidMobile:true},
		                  "memberBase.pPhone":{isValidPhone:true}
		                },  
		                messages: {    
		                  "memberTypeId":{required:"请选择会员类型"},
		                  "memberBase.province":{ required:"请选择所在省份"},  
		                  "memberBase.city":{ required:"请选择所在城市"},  
		                  "memberBase.pName":{ required:"请填写会员姓名"},  
//		                  "memberBase.eAddress":{ required:"请填写详细地址"},
//		                  "memberBase.eOrgCode":{ required:"请填写组织机构代码"},
// 		                  "orgCertificate":{required:"请上传组织机构代码证"},
// 		                  "memberBase.eBusCode":{ required:"请填写营业执照代码",isValidBusCode:"营业执照号码不正确"},
// 		                  "busLicense":{required:"请上传营业执照扫描图"},
// 		                  "memberBase.eTaxCode":{ required:"请填写税务登记号码"},
// 		                  "memberBase.eNature":{ required:"请填写企业性质"},
		                  
		            //      "memberBase.pMobile":{required:"请填写移动电话",isValidMobile:"移动电话号码格式错误，正确格式为：13708710118"},
		           //       "memberBase.eContact":{ required:"请填写联系人"},
		           //       "memberBase.eContactPhone":{ required:"请填写联系人固话"},
// 		                  "banklibrary_id":{ required:"请选择开户行"}, 
// 		                  "memberBase.bank":{ required:"请填写开户行全称"}, 
		                  "memberBase.code":{ required:"请填写登录名"},  
		                  "memberBase.pName":{required:"请填写姓名"},
		                  "memberBase.pSex":{required:"请填写性别"},
		                  "memberBase.pPhone":{required:"请填写固定电话"},
		                  "memberBase.pAddress":{required:"请填写住址"},
		                  "memberBase.pBirthday":{required:"请填写出生年月"},
		                  "memberBase.idCardNo":{required:"请填写身份证号码"},
 		                  "memberBase.email":{required:"请填写电子邮箱"},
// 		                  "idCardBackImg":{required:"请上传身份证反面扫描图"},
// 		                  "bankCardFrontImg":{required:"请上传银行卡正面扫描图"},
// 		                  "accountApplicationImg":{required:"请上传开户申请书第一页"},
// 		                  "accountApplicationImg1":{required:"请上传开户申请书第二页"},
		                  "memberBase.pMobile":{required:"请填写手机号",isValidMobile:"手机号码格式错误，正确格式为：13708710118"},
		                  "memberBase.pPhone":{isValidPhone:"固定电话格式错误，正确格式为：0871-9090980,0871-9090980-321"},
		               //   "memberBase.eContactPhone":{isValidPhone:"固定电话格式错误，正确格式为：0871-9090980,0871-9090980-321"},
		               //   "memberBase.ePostcode":{isValidPostCode:"邮政编码格式错误，正确格式为6位数字，如：615206"}
		                }, submitHandler: function(form) {  //通过之后回调 
                            //alert(form); 
                            if(checkData()){
                            form.submit();//提交表单，如果不写，即便通过表单也不会自动提交 
                            }
                       }
		                    
		        });   
		        
			/* $("#file_image").uploadify({
					        'uploader': '/wisdoorStatic/js/jquery.uploadify-v2.1.0/uploadify.swf',
					        'script': '/sysCommon/sysFileUpload!saveUploadFiles?time='+ new Date().getTime(),
					        'cancelImg': '/wisdoorStatic/js/jquery.uploadify-v2.1.0/cancel.png',
					        'folder': '/wisdoorStatic/userfiles/',
					        'fileDataName' :'filedata',
					        'queueID': 'fileQueue',
					        'buttonText': '请选择资料',  
					        'auto': true,   
					        'multi': true,
					        'fileExt':'*.gif;*.jpg;*.bmp;*.png;*.doc;*.docx;*.xls;*.xlsx;*.zip;*.war;*.pdf',
					        'fileDesc':'*.gif;*.jpg;*.bmp;*.png;*.doc;*.docx;*.xls;*.xlsx;*.zip;*.war;*.pdf',  
					        onSelect: function(e, queueId, fileObj){
					        	$('#tooltip').html("");
					        	$('#tooltip').html("<img src='/wisdoorStatic/images/uploading.gif' style='border:0;' />正在附件加载中，请稍后！");
					        },
					        onComplete:function(event,queueId,fileObj,response,data){ 
					        	$('#tooltip').html(""); 
					        	  var file = eval("("+response+")"); 
								  $("#responseFiles").append("<span id="+(file[0].frontId)+">"+(file[0].fileName)+"<img src='/wisdoorStatic/images/no.gif' style='border:0;' onclick=\"delFile('"+file[0].frontId+"','"+file[0].id+"');\"/><input type=\"hidden\" name=\"fileIds\" value=\""+(file[0].id)+"\" /><br/></span>");  
				       
					        }
	          });     */
	
		    //时间控件
		    $("#birthday").datepicker({
		        showOn: 'button',
		        buttonImageOnly: false,
		        stepMonths:12,
		        prevText:"上一年",
		        nextText:"下一年",
		        //yearRange:"15:01",
		        changeMonth: true,
		        changeYear: true,
		        numberOfMonths: 1,
		        dateFormat: "yy-mm-dd",
		        beforeShow: function(input, inst) { 
		         setIframeHeight(180);
		        }
		    });
		    $("#ui-datepicker-div").css({'display':'none'});

		    $('#memberBase_province').change(function(){
			    $.post('/back/region/regionAction!getchildren?region_id='+$(this).val(),null,function(data,status){
			    	$('#memberBase_city').children().remove();
			    	$('#memberBase_city').html(data);
				},'text');
				
			});
		}); 

	//删除文件
		function delFile(frontId,fileId){ 
		   $.getJSON("/sysCommon/sysFileUpload!delete?time=" + new Date().getTime() + "&fileId="+fileId, function(data){
			if(data=="1"){  
				$("#"+frontId).remove(); 
			} 
		});
		}
		
	/**
	*根据用户选择的会员类别显示相应的界面
	*/				
	function changeCategory(checked,category){
		if(checked){
			$("#memberCategory").val(category);
			if("0"==category){
				$(".person_member").show();
				$("#idCardNo").text("身份证号：");
				if((""!="${id}")){
					$("#memberTypeId").hide();
				}else{
					$("#memberTypeId").show();
				}
			}/* else{
				//企业
				$(".org_member").show();
				$(".person_member").hide();
				$("#person_member_type_name").hide();
				if((""!="${id}")){
					$("#memberTypeId").hide();
				}else{
					$("#memberTypeId").show();
				}
				//$("#idCardNo").text("法人身份证号："); 
			}*/
		}
	}
	
	/**
	*验证个人会员的身份证号或营业执照号是否已开过会员
	*/
	function checkData(){
	  var ret=true;
	  var codeNo="";
	  var category=$("#memberCategory").val();
	  var memberTypeId=$("#memberTypeId").val();
	  //alert("memberTypeId= "+memberTypeId);
	  if("0"==category){
	  //个人会员
	   codeNo=$("input[type='text'][name='memberBase.idCardNo']").val();
	   //alert("category="+category+",codeNo="+codeNo);
	   if(""!=codeNo && isValidIdCard(codeNo)){
	    //身份证号码格式正确，并且是新会员开户  
	  	$.ajax({
	    url:"/back/member/memberBaseAction!checkIdCard",
	    data:{"id":"${id}","idCard":codeNo,"memberTypeId":memberTypeId},
	    type:"post",
	    async:false,
	    success:function(data,textStatus){
	      //alert(data);
	      //后台返回true，说明身份证此号码已经开过户
	      if(1==data){
 				$("#idCard").text("此身份证号码已在使用"); 
 				$("#idCard").show();
 				ret=false;
 			  }else{
 				    //alert("此身份证号码未使用");	
 				    $("#idCard").text("");
 				    $("#idCard").hide();
 				}
	    },error:function(textStatus,xmlHtmlRequest,errorThrow){
	       JSON.stringify(xmlHtmlRequest);
	     }
	  });
	  }
	  }
	  return ret;
	}
	
	/**
	*判断是否有必要显示银行卡扫描图
	*/
	/* function isNecessary(){
	  var ret=false;
	  ret="融资方"!=$("#memberTypeId").find("option:selected").text();
	  //alert("ret="+ret);
	  if(!ret && !${change}){
	     $("#application1Needed").show();
	  }else{
	  $("#application1Needed").hide();
	  }
	  return ret;	  
	} */
	
	/**
	*显示或隐藏银行卡扫描图
	*/
	/* function changeMemberType(){
	  if(isNecessary()){
	    //
	    $(".financier").show();
	  }else{
	    $(".financier").hide();
	  } */
	  

	
	

	
	/**
	*是否需要开户申请书第二页
	*/
	/* function isAccountApplicationImg1Needed(){
	  var ret=true;
	  //alert($("#accountApplicationImg1"));
	  $("#applicationImg1").html("");
	  $("#applicationImg1").hide();
	  if(!isNecessary() == true && ""==$("#accountApplicationImg1").val() && true==${change==false}){
	    
	    //alert("请选择开户申请书第二页！");
	    
	    ret=false;
	    $("#applicationImg1").html("请上传开户申请书第二页");
	    $("#applicationImg1").show();
	  }
	  return ret;
	} */
	
	/**
	*检测图片的大小
	*/
/* 	function checkImgSize(obj){
	       
	       var allowSize=1024*1024;//1M
	       var selectedFileSize=0;
	       if(obj.attribute) 
            {   //alert(obj);
                //ie 
                if (window.navigator.userAgent.indexOf("MSIE")>=1) 
                { 
                    var img= document.createElement("img");
                    img.src="file://"+obj.value;
	                obj.select(); 
	                img.src="file://"+document.selection.createRange().text;
	                //img.src="file://"+obj.createRange().text;
                    //obj.select(0,0);
                    //obj.blur();
                    selectedFileSize = img.fileSize; 
                } 
                //firefox 
                else if(window.navigator.userAgent.indexOf("Firefox")>=1) 
                { 
                   if(obj.files) 
                    { 
                        var item=obj.files.item(0);                        
                        selectedFileSize= item.size; 
                    } 
                } 
            } 	        
	        return selectedFileSize <= allowSize;
	  } */
	
	function preview(obj){
	       //alert(getFullPath(obj));
	       var img= document.createElement("img");
	       
	       img.src="file://"+getFullPath(obj);
	       
	       //$("#preview").append(img);
	       //$("#imgPreviewer").attr("src",img.src);
	       //alert(img.fileSize);
	}
	
	 function getFullPath(obj) 
        { 
            if(obj) 
            { 
                //ie 
                if (window.navigator.userAgent.indexOf("MSIE")>=1) 
                { 
                    obj.select(); 
                    //alert(obj.fileSize);
                    return document.selection.createRange().text; 
                } 
                //firefox 
                else if(window.navigator.userAgent.indexOf("Firefox")>=1) 
                { 
                   if(obj.files) 
                    { 
                        var item=obj.files.item(0);
                        //alert("fileSize="+item.size);
                        return item.mozFullPath; 
                    } 
                    return obj.value; 
                } 
                return obj.value; 
            } 
        } 

</script>
	</head>
	<body>
	  <s:property value="exception.message" /> 
		<form action="/back/member/memberBaseAction!save" method="post"
			id="memberbaseform" enctype="multipart/form-data">
			<input type='hidden' class='autoheight' value="auto" />
			<table cellspacing="8px;">
				<tr>
					<td style="border: 1px;">
						<div style="border: 1px;">
							<table>  
							   <tr><td colspan="2"><s:actionerror cssStyle="font-size:13px;color:#B82525"/><s:actionmessage cssStyle="font-size:16px;;color:#DC1818"/></td></tr>
								
								
								<tr>
									<td align="right">									
										<span class="title">介绍人：</span>
									</td>
									<td>
										<input size="55" class="input_box" style="width: 170px;" name="memberBase.creator" value="${memberBase.creator.username}" type="text" disabled="true"  />
									</td>
								</tr>
								
								<tr>
									<td align="right">									
										<span class="title">辅导人：</span>
									</td>
									<td>
										<input size="55" class="input_box" style="width: 170px;" name="memberBase.fudaoren" value="${memberBase.fudaoren}" type="text" />
									   <%--  <span class="title">&nbsp;&nbsp;辅导人姓名：</span>
										<input maxlength="17" class="input_box" style="width: 170px;"
											name="memberBase.email" type="text" id="memberBase.email"
											value="${memberBase.email}"
											onkeyup="this.value=this.value.replace(/[^(\d|\-)]/g,'')" /> --%>
									
									</td>
								</tr>
                                
								<tr>
									<td align="right">
										<span style="color: red">*</span><span class="title">会员类别：</span>
									</td>
									<td>
									    <input type="hidden" id="memberCategory" name="memberCategory" value="${memberBase.category}"/>
									    <s:if test="null==id">
											<s:radio id="memberCategory" list="categories" name="memberBase.category" onclick="changeCategory(this.checked,this.value);" disabled="true"></s:radio>
										</s:if>
									    <s:else>
									    <input type="hidden" name="memberBase.category" value="${memberBase.category}" />
									    <span><s:if test="0==memberBase.category">个人</s:if>
                                           <s:elseif test="1==memberBase.category">中心</s:elseif></span>
									    </s:else>
									</td>
								</tr>
								<tr>
									<td align="right">
										<input type="hidden" name="id" value="${id}" />
										<span style="color: red">*</span><span class="title">会员类型：</span>
									</td>
									<td>
									    <s:select id="memberTypeId" cssClass="input_select" name="memberTypeId" 
									        headerKey="" headerValue="请选择" list="memberTypes" 
									        listKey="id" listValue="name" onchange="changeMemberType()"
											cssStyle="padding:1px;width:100px;"></s:select>
									    <s:if test="null==id">
									    <span id="person_member_type_name" style="display: none;"></span>
									    </s:if>
									    <s:else>									      
									      <span>${memberBase.memberType.name}</span>
									    </s:else>
									</td>
								</tr>
								
								
								<tr>
									<td align="right">
										<span style="color: red">*</span><span class="title">所在区域：</span>
									</td>
									<td>
										<s:select cssClass="input_select" list="regions_province"
											id="memberBase_province" value="memberBase.province"
											headerKey="" headerValue="请选择"
											name="memberBase.province" listKey="areacode"
											listValue="areaname_s" cssStyle="padding:1px;width:80px;"></s:select>
										<s:select cssClass="input_select" list="regions_city"
											id="memberBase_city" value="memberBase.city"
											headerKey="" headerValue="请选择"
											name="memberBase.city" listKey="areacode"
											listValue="areaname_s" cssStyle="padding:1px;width:80px;"></s:select>
										
									</td>
								</tr>
								
								<tr class="person_member">
								
								    <td align="right">									
										<span style="color: red">*</span><span class="title">登录名：</span>
									</td>
									<td>
										<input size="55" class="input_box" style="width: 170px;" name="memberBase.code" value="${memberBase.code}" type="text" />
									   <%--  <span class="title">&nbsp;&nbsp;辅导人姓名：</span>
										<input maxlength="17" class="input_box" style="width: 170px;"
											name="memberBase.email" type="text" id="memberBase.email"
											value="${memberBase.email}"
											onkeyup="this.value=this.value.replace(/[^(\d|\-)]/g,'')" /> --%>
									
									</td>
								</tr>
								
								<tr class="person_member">
									<td align="right">
										<span style="color: red">*</span><span class="title">姓名：</span>
									</td>
									<td>
										<input class="input_box" style="width: 170px;" maxlength="32"
											name="memberBase.pName" type="text" id="memberBase.pName"											
											value="${memberBase.pName}" />
										<span class="title">&nbsp;&nbsp;性别：</span>
										<s:select cssClass="input_select" name="memberBase.pSex"
											id="memberBase.pSex" list="sexes" listKey="key"
											cssStyle="padding:1px;width:50px;"
											listValue="value"></s:select>
									</td>
								</tr>
								
								
								<tr class="person_member">
									<td align="right">
										<span class="title">出生年月：</span>
									</td>
									<td>
										<input class="input_box" style="width: 145px;" maxlength="10"
											readonly="readonly" name="birthday" type="text" id="birthday"
											value="<fmt:formatDate value="${memberBase.pBirthday}" pattern="yyyy-MM-dd"/>" />
										<span class="title">&nbsp;&nbsp;学历：</span>
										<s:select cssClass="input_select" name="memberBase.edu"
											id="memberBase.edu" list="edues" listKey="key"
											cssStyle="padding:1px;width:50px;"
											listValue="value"></s:select>	
									    
									</td>
									
								</tr>
								<tr>
									<td align="right">
										<span style="color: red">*</span><span id="idCardNo"
											class="title">身份证号：</span>
									</td>
									<td>
										<input maxlength="18" class="input_box" style="width: 300px;"
											name="memberBase.idCardNo" type="text"
											value="${memberBase.idCardNo}"
											/>
										<input name="firstIdCardNo" type="hidden"
                                            value="${memberBase.idCardNo}"
                                            />
										<label class="error" style="display: none;" generated="false"
											for="memberBase.idCardNo" id="idCard"></label>
									</td>
								</tr>
								<tr class="person_member">
								
								    <td align="right">									
										<span style="color: red">*</span><span class="title">电子邮箱：</span>
									</td>
									<td>
										<input maxlength="17" class="input_box" style="width: 170px;"
											name="memberBase.email" type="text" id="memberBase.email"
											value="${memberBase.email}"
											 />
									
									</td>
								</tr>
								<tr class="person_member">
									<td align="right">
										<span style="color: red">*</span><span class="title">联系电话：</span>
									</td>
									<td>
										<input maxlength="11" class="input_box" style="width: 170px;"
											name="memberBase.pMobile" type="text" id="memberBase.pMobile"
											value="${memberBase.pMobile}"
											onkeyup="this.value=this.value.replace(/[^(\d)]/g,'')" />
									</td>
								</tr>
								
								
								<%-- <tr>
									<td align="right">
										<span style="color: red">*</span><span class="title">开户行：</span>
									</td>
									<td colspan="3">
										<s:select id="banklibrary_id" list="banklibrary" cssStyle="padding:1px;width:100px;"
										headerKey="" headerValue="请选择"
										name="banklibrary_id" cssClass="input_select" listKey="id" listValue="caption" value="memberBase.banklib.id"></s:select>
                                                                                                  
                                                                                                  							
									</td>
								</tr>
								<tr>
									<td align="right">
									   <span style="color: red">*</span><span class="title">开户行全称：</span>
									</td>
									<td colspan="3">
									    <input maxlength="16" class="input_box" style="width: 400px;"
											name="memberBase.bank" type="text" id="memberBase.bank"
											value="${memberBase.bank}" 
									</td>
								</tr> --%>
							
								
								<tr class="person_member">
									<td align="right">
										<span style="color: red">*</span><span class="title">住址：</span>
									</td>
									<td colspan="3">
										<input class="input_box" type="text" style="width: 300px;"
											maxlength="100" name="memberBase.pAddress"
											id="memberBase.pAddress" value="${memberBase.pAddress}" />
									</td>
								</tr>
								
								
						<%-- 		<tr>
									<td align="right">									
										<span class="title">经办人：</span>
									</td>
									<td>
										<input size="55" class="input_box" style="width: 400px;" name="memberBase.jingbanren" value="${memberBase.jingbanren}" type="text" />
									</td>
								</tr> --%>
								<%-- <tr>
									<td align="right">									
										<span class="title">QQ：</span>
									</td>
									<td>
										<input size="55" class="input_box" style="width: 170px;" name="memberBase.qq" value="${memberBase.qq}" type="text" />
									</td>
								</tr>
								<tr>
									<td align="right">									
										<span class="title">Email：</span>
									</td>
									<td>
										<input size="55" class="input_box" style="width: 400px;" name="memberBase.email" value="${memberBase.email}" type="text" />
									</td>
								</tr> --%>
								
								
								
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="1">
					
						<input class="ui-state-default" type="submit" value="保存" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
