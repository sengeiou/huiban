var statue = 0; //试题答题状态，是否允许答题1允许 ，0禁止
/**
 * 设置题目状态
 * @param {int} statue1
 */
function setStatue(statue1) {
	statue = statue1;
	log(statue);
}
/**
 * 设置监听，答题
 */
function setClickListener() {
	if(statue > 0) {
		setClick();

	} else {
		$(".answer").click(function() {
			var type = $(this).attr("type");
			var examId = $(this).attr("examId");
			log(type + ", " + examId);
			if(window.test != undefined) {
				window.test.examAnalysis("", "", "", examId, type);
			}
		})
	}
}
/**
 * 添加标题
 * @param {int} num 序号：一
 * @param {String} title 标题内容
 */
function add_title(num, title) {
	//var num_=(num+'').replace(/、|\t/g,"");
	var num_ = (num + '').replace(/、/g, "");
	var text = '<h4>' + num_ + '、' + title + '</h4>'
	$("#content").append(text);
}
/**
 * 添加题目内容
 * @param {int} num 序号1
 * @param {String} content 题目内容
 * @param {int} answer_statue 正确答案
 */
function add_problem_content(num, content, examId, type, answer_statue) {
	var num_text = '';
	if(num != undefined && (num + '').length > 0) {
		num_text = num + '.';
	}
	if(content == undefined) {
		content = "";
	}
	var text_explaint = '';
	if(statue < 1) {
		//text_explaint = add_explain_answer(answer_statue, examId, type);
	}
	var text = '<div class="question">' + '<span class="float_left">' + num_text + '</span>' + content + '</div>' + text_explaint + '<br />';
	$("#content").append(text);
}

function add_explain_answer(answer_statue, examId, type) {
	var img;
	var imgs = '';
	log('expalint = ' + answer_statue + ', ' + statue);
	var class_name = 'answer_explain_div';
	var color="00A3F2";
	switch(Number(answer_statue)) {
		case 1: //答对
			img = "correct_icon";
			imgs = '<img src="img/' + img + '.png" class="imgtype"/>'
			break;
		case 2: //答错
			img = "error_icon";
			imgs = '<img src="img/' + img + '.png" class="imgtype"/>'
			class_name = 'answer_explain_div_error';
			color="F7192E";
			break;
		case 3: //半对
			img = "half_icon";
			imgs = '<img src="img/' + img + '.png" class="imgtype"/>'
			//color="F7192E";
			break;
		case 0: //未批阅
			img = "noread_icon";
			imgs = '<img src="img/' + img + '.png" class="imgtype"/>'
			class_name = 'answer_explain_div_noread';
			color="7C7C7C";
			break;
	}
	var text = '<div class="answer" examId="' + examId + '" type="' + type + '" style="border-color:#'+color+';"><div style="width: 50%;height: 100%;float: left;">' + imgs + '</div><div class="' + class_name + '" examId="' + examId + '" type="' + type + '">答案解析</div></div>'
	return text;
}
/**
 * 填空题、主观题填写图表
 * @param {String} path 文件标识
 * @param {String} name 文件地址/本地名字
 * @param {String} class_name 
 * @param {String} src_name 图片答案的地址
 */
function structure_img(path, name, class_name, src_name) {
	if(path == "img") {
		name = "img/" + name;
	}
	var name_attr = '';
	if(src_name != undefined && src_name.length > 0) {
		name_attr = 'name="' + src_name.toString() + '"';
	}
	var content = '<img src = "' + name + '" class="' + class_name + '"' + name_attr + '/>';
	return content;
}

function structure_img_fill(path, name, class_name, src_name, id, num) {
	if(path == "img") {
		name = "img/" + name;
	}
	var name_attr = '';
	log(src_name);
	if(src_name != undefined && src_name.length > 0) {
		name_attr = 'name=' + src_name + '';
	}
	log(name_attr)
	var content = '<img src = "' + name + '" class="' + class_name + '"' + name_attr + ' id="' + (id + '' + num) + '"num="' + num + '"/>';
	return content;
}
/**
 * 添加试题为空提醒
 */
function add_empty_content() {
	var text = '<span class="empty_span">暂时没有试题</span>';
	$("#content").append(text);
}
/**
 * 添加选择题ABCD选项
 * @param {Array数组} select_array
 * @param {Array} answer
 */
function add_select_answer_option(select_array, answer, tag) {
	var text = '';
	if(select_array != undefined && select_array.length > 0) {
		select_array.sort(function(a, b) { //排序
			return a.localeCompare(b);
		});
		$.each(select_array, function(i, item) {
			var class_name;
			if($.inArray(item, answer) != -1) {
				class_name = "radio_select_bg";
			} else {
				class_name = "radio_unselect_bg";
			}
			text += structure_select_answer_option_content(class_name, item);
		});
	} else {
		text = "无选项（此试题错误）";
	}
	$("#content").append('<div class="clear_float" tag="' + tag + '">' + text + '</div><br />');
}
/**
 * 构造选择题单个ABCD选项 或对错
 * @param {String} class_name
 * @param {String} selete_content 选项值ABCD
 */
function structure_select_answer_option_content(class_name, selete_content) {
	var text = '<label class="' + class_name + '" >' + selete_content + '</label>';
	return text;
}
/**
 * 选择题选项切换
 * @param {$("")} ele $(.class)
 */
function change_radio(ele) {
	var class_name = ele.attr('class');
	//ele.attr('class', 'radio_select_bg');
	var div_parent = ele.parent();
	var tag = div_parent.attr('tag');
	var num = div_parent.attr('name');
	//var array_num = split_array(num);
	if(class_name == "radio_unselect_bg") {
		var type;
		if(tag != undefined && tag != 'undefined') { //多选
			type = 1;
		} else {
			ele.parent().children('label').attr('class', 'radio_unselect_bg'); //全部取消，单选
		}
		ele.attr('class', 'radio_select_bg');
		update_data(ele.text(), type);
		log("radio_unselect_bg-->radio_select_bg " + ele.text() + ', ' + array_num[0] + ', ' + array_num[1]);
	} else if(class_name == "radio_select_bg") { //点击不取消已选项
		if(tag != undefined && tag != 'undefined') {
			ele.attr("class", "radio_unselect_bg");
			update_data(ele.text(), -1);

			console.log("radio_select_bg-->radio_unselect_bg " + ele.text());
		}
	}
}
/**
 * 拆分
 * @param {String} num
 * @return {Array} 
 */
function split_array(num) {
	return num.split("|");
}
/**
 * 填空题
 * @param {int} examId
 * @param {type} num
 * @param {Object} type_answer 答案类型 废弃
 * @param {Object} new_answer 填空题图片答案
 */
function fill_problem(id, type_answer, new_answer, sort, content, answer_statue) {
	var name;
	var content;
	var class_name;
	var answer;
	var num_text = '题目：';
	
	if(content == undefined) {
		content = "";
	}
	var text_explaint = '';
	
	var answer_="";
	if(typeof(new_answer)=='object'){
		answer_ = String(arrayToString(new_answer));//&apos;
	}else{
		var answer_arr=new Array(1);
		answer_arr[0]=new_answer;
		answer_ = String(arrayToString(answer_arr));
	}

	//console.log("fill=" + answer_);
	//消除引号影响
	answer_=answer_.replace(/\"/g,'lopadfgoegw150');
	var text = '<div class="question fill"  id="' + id + '" myType="fill" answserStu = ' + answer_ + ' >' + '<div class="float_left">' + num_text + '</div>' + content + '</div>' + text_explaint + '<br />';
	$("#content").append(text);
	return true;
}

function isContains(source, target) {
	return source.indexOf(target) >= 0;
}
/**
 * 主观题
 * @param {int} examId
 * @param {type} num
 * @param {Object} type_answer 废弃
 * @param {Object} answer
 */
function subjective_problem(id, type_answer, answer) {
	var content;
	log(answer);
	if(answer != undefined && answer.length > 0) {
		add_imag_text(id, num, answer);
		return;
	} else {
		if(statue < 1) {
			structure_text_span(id, num, '无')
			return;
		}
		content = structure_img('img', 'air_nomal.png', 'write_img', undefined);
	}
	$("#content").append('<div class="clear_float" id="' + id + '" myType="subjective">' + content + '</div>');
}
/**
 * 为编辑器做准备
 * @param {Object} id
 * @param {Object} num
 * @param {Object} answer
 */
function add_imag_text(id, num, answer) {
	var class_ = "img_text";
	var add_res='';
	//  /static/js/plugin/xheditor/xheditor_emot/default/tongue.gif
	//  js/xheditor_emot/default/angry.gif
	answer=answer.replace(/\/static\/js\/plugin\/xheditor/g,"js");
	if(statue>0){
		add_res=add_revise_answer();
	}
	var content = '<div class="img_text">' + answer + '</div>';
	var text = '<div class="clear_float" id="' + id + '"name="' + num + '" myType="subjective">' + content + add_res + '</div>';
	$("#content").append(text);
}

/**
 * 构造修改答案
 */
function add_revise_answer() {
	return '<div class="revise_div"> <button class="revise_button">修改答案</button> </div>';
}

function structure_text_span(id, num, answer) {
	var s = '';
	if(typeof(answer) == 'object') {
		s = answer[0];
	} else {
		s = answer;
	}
	content = '<div class="clear_float" id="' + id + '"name="' + num + '"><a class="text_span">' + s + '</a></div><br/>';
	$("#content").append(content);
}
/**
 * 构造文本<a>
 * @param {int} id
 * @param {type} num
 * @param {Object} answer
 */
function structure_text_a(id, num, answer) {
	var s = '';
	if(typeof(answer) == 'object') {
		s = answer[0];
	} else {
		s = answer;
	}

	content = '<div class="clear_float" id="' + id + '"name="' + num + '"><a class="text_a">' + s + '</a></div><br/>';
	$("#content").append(content);
}
/**
 *
 * @param {Object} id
 * @param {Object} num
 * @param {Object} answer
 */
function structure_a(id, num, answer) {
	var content = '<a id="' + (id + '' + num) + '" num="' + num + '" class="text_a">' + answer + '</a>';
	return content;
}
/**
 * 判断题
 * @param {type} num
 * @param {String} answer 答案，对、错
 */
function judge_problem(answer) {
	var text = '';
	switch(answer) {
		case '对':
			text += structure_select_answer_option_content('radio_select_bg', '对');
			text += structure_select_answer_option_content('radio_unselect_bg', '错');
			break;
		case '错':
			text += structure_select_answer_option_content('radio_unselect_bg', '对');
			text += structure_select_answer_option_content('radio_select_bg', '错');
			break;
		default:
			text += structure_select_answer_option_content('radio_unselect_bg', '对');
			text += structure_select_answer_option_content('radio_unselect_bg', '错');
	}
	$("#content").append('<div class="clear_float" name="' + num + '">' + text + '</div><br />');
}
/**
 * 区分题目类型并加载，
 * @param {int} num1 列表第一层序号
 * @param {int} num2 子列表序号
 * @param {String} question_type 类型
 * @param {Object} data
 * @return {void}
 */
function division_question(data) {
	var answer_stu = data.stuAnswer; //学生答案
	var answer_statue = data.status;
	var examId = data.examId;
	var sort = data.sort;
	if(sort == undefined) {
		sort = num2 + 1;
	}
	logTag(data.optionName, (answer_stu));
	switch(data.optionName) {
		case 'radio': //单选
			//console.log(data.sort);
			//log(sort);
			add_problem_content(sort, data.content, examId, 'radio', answer_statue);
			add_select_answer_option( data.option, new Array(answer_stu));
			break;
		case 'check': //多选
			add_problem_content(sort, data.content, examId, 'check', answer_statue);
			if(typeof(answer_stu) == 'object') {
				add_select_answer_option(data.option, answer_stu, "check");
			} else {
				log(answer_stu);
				var arrays = answer_stu.split("");
				add_select_answer_option(data.option, arrays, "check");
			}
			break;
		case 'fill': //填空题
			//add_problem_content(sort, data.content, examId, 'fill', answer_statue);
			//console.log(answer_stu);
			fill_problem(examId, data.ansType, answer_stu, sort, data.content, answer_statue);
			break;
		case 'judge': //判断
			add_problem_content(sort, data.content, examId, 'judge', answer_statue);
			judge_problem(answer_stu);
			break;
		case 'subjective': //主观题
			add_problem_content(sort, data.content, examId, 'subjective', answer_statue);
			//if(answer_stu)
			subjective_problem(examId,data.ansType, answer_stu);
			break;
		case '': //判断题
			break;

	}
}

function arrayToString(array) {
	var s = '';
	$.each(array, function(i, item) {
		if(i == 0) {
			s = item;
		} else {
			s += (',' + item);
		}
	});
	return s;
}

function logTag(tag, text) {
	if(tag == undefined) {
		tag = "";
	} else {
		tag += " = ";
	}
	log(tag + text)
}

function log(text) {
	if(window != undefined && window.test != undefined) {
		//window.test.log(text);
	} else {
		console.log(text);
	}
}