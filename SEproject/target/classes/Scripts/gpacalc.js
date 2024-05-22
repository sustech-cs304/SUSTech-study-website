function getnum(f, c) {
    var t = Math.pow(10, c);
    return Math.round(f * t) / t
}

$(document).ready(function () {
    var hideSecColumn = function (){
        $('.secColumn').hide();
    }
    $.random = function (min, max) {
        var rand = parseInt(Math.random() * (max - min + 1) + min);
        return Math.round(rand)
    };
    var dataObj = [{
        standardGPA: 0,
        commonGPA: []
    }, {
        standardGPA: 0,
        commonGPA: 0
    }, {
        standardGPA: '--',
        commonGPA: 0
    }];
    var arithmetic = {
        "hundred": {
            "standard_4": [{
                "rank": [90, 100],
                "point": 4
            }, {
                "rank": [80, 89],
                "point": 3
            }, {
                "rank": [70, 79],
                "point": 2
            }, {
                "rank": [60, 69],
                "point": 1
            }, {
                "rank": [0, 59],
                "point": 0
            }],
            "improve1_4": [{
                "rank": [85, 100],
                "point": 4
            }, {
                "rank": [70, 84],
                "point": 3
            }, {
                "rank": [60, 69],
                "point": 2
            }, {
                "rank": [0, 59],
                "point": 0
            }],
            "improve2_4": [{
                "rank": [85, 100],
                "point": 4
            }, {
                "rank": [75, 84],
                "point": 3
            }, {
                "rank": [60, 74],
                "point": 2
            }, {
                "rank": [0, 59],
                "point": 0
            }],
            "Beida_4": [{
                "rank": [90, 100],
                "point": 4
            }, {
                "rank": [85, 89],
                "point": 3.7
            }, {
                "rank": [82, 84],
                "point": 3.3
            }, {
                "rank": [78, 81],
                "point": 3
            }, {
                "rank": [75, 77],
                "point": 2.7
            }, {
                "rank": [72, 74],
                "point": 2.3
            }, {
                "rank": [68, 71],
                "point": 2.0
            }, {
                "rank": [64, 67],
                "point": 1.5
            }, {
                "rank": [60, 63],
                "point": 1.0
            }, {
                "rank": [0, 59],
                "point": 0
            }],
            "Canada_43": [{
                "rank": [90, 100],
                "point": 4.3
            }, {
                "rank": [85, 89],
                "point": 4.0
            }, {
                "rank": [80, 84],
                "point": 3.7
            }, {
                "rank": [75, 79],
                "point": 3.3
            }, {
                "rank": [70, 74],
                "point": 3.0
            }, {
                "rank": [65, 69],
                "point": 2.7
            }, {
                "rank": [60, 64],
                "point": 2.3
            }, {
                "rank": [0, 59],
                "point": 0
            }],
            "USTC_43": [{
                "rank": [95, 100],
                "point": 4.3
            }, {
                "rank": [90, 94],
                "point": 4.0
            }, {
                "rank": [85, 89],
                "point": 3.7
            }, {
                "rank": [82, 84],
                "point": 3.3
            }, {
                "rank": [78, 81],
                "point": 3.0
            }, {
                "rank": [75, 77],
                "point": 2.7
            }, {
                "rank": [72, 74],
                "point": 2.3
            }, {
                "rank": [68, 71],
                "point": 2.0
            }, {
                "rank": [65, 67],
                "point": 1.7
            }, {
                "rank": [64, 64],
                "point": 1.5
            }, {
                "rank": [61, 63],
                "point": 1.3
            }, {
                "rank": [60, 60],
                "point": 1.0
            }, {
                "rank": [0, 59],
                "point": 0
            }],
            "SJTU_43": [{
                "rank": [95, 100],
                "point": 4.3
            }, {
                "rank": [90, 94],
                "point": 4.0
            }, {
                "rank": [85, 89],
                "point": 3.7
            }, {
                "rank": [80, 84],
                "point": 3.3
            }, {
                "rank": [75, 79],
                "point": 3.0
            }, {
                "rank": [70, 74],
                "point": 2.7
            }, {
                "rank": [67, 69],
                "point": 2.3
            }, {
                "rank": [65, 66],
                "point": 2.0
            }, {
                "rank": [62, 64],
                "point": 1.7
            }, {
                "rank": [60, 61],
                "point": 1.0
            }, {
                "rank": [0, 59],
                "point": 0
            }]
        },
        "five": [{
            "rank": 5,
            "point": 4
        }, {
            "rank": 4,
            "point": 3
        }, {
            "rank": 3,
            "point": 2
        }, {
            "rank": 2,
            "point": 1
        }],
        "rank": [{
            "rank": "A",
            "point": 4
        }, {
            "rank": "A-",
            "point": 3.7
        }, {
            "rank": "B+",
            "point": 3.3
        }, {
            "rank": "B",
            "point": 3
        }, {
            "rank": "B-",
            "point": 2.7
        }, {
            "rank": "C+",
            "point": 2.3
        }, {
            "rank": "C",
            "point": 2
        }, {
            "rank": "C-",
            "point": 1.7
        }, {
            "rank": "D+",
            "point": 1.3
        }, {
            "rank": "D",
            "point": 1
        }, {
            "rank": "F",
            "point": 0
        }]
    };
    var makeArithOpts = function () {
        score_type = $('.scoreType').val();
        var opts_html = score_type == 'hundred' ? '<option value="standard_4" name="标准4.0算法">标准4.0算法</option><option value="improve1_4" name="改进4.0算法(1)">改进4.0算法(1)</option><option value="improve2_4" name="改进4.0算法(2)">改进4.0算法(2)</option><option value="Beida_4" name="北大4.0算法">北大4.0算法</option><option value="Canada_43" name="北大4.0算法">加拿大4.3算法</option><option value="USTC_43" name="中科大4.3算法">中科大4.3算法</option><option value="SJTU_43" name="上海交大4.3算法">上海交大4.3算法</option>' : score_type == 'five' ? '<option value="five" name="五分制算法">五分制算法</option>' : score_type == 'rank' ? '<option value="rank" name="等级制算法">等级制算法</option>' : '<option value="">请选择算法</option>';
        if($('.scoreType').val() == 'hundred'){
            $('.trLine').remove();
            createHandredItems();
            reset();
            var bbbb = $('.arithmeticType').size();
            $('.arithmeticType').each(function (index) {
                $(this).html(opts_html);
                $(this)[0].options[index].selected = true;
                $('#spanID' + (parseInt(index)+1)).html($(this)[0].options[$('.trLine').size()-1-parseInt(index)].innerHTML);
            });
        }
        else{
            $('.arithmeticType').html(opts_html);
            $('#spanID1').html($('.arithmeticType')[0].options[0].innerHTML);
        }
        hideSecColumn();
    };
    var reset = function () {
        var disabled = $($('.name')[0]).attr('disabled');
        if (!disabled) $('.name').attr('disabled', true);
        $('.score,.credit').css({
            "color": "#333"
        }).val('');
        $('.name').css({
            "color": "#BBB",
            "background": "#DDD"
        });
    };
    var createHandredItems = function (){
        if($('.scoreType').val() == 'hundred'){
            for(var i = 1; i < 8; i++){
                var item = $('<tr class="trLine"><td><select name="arithmeticType" class="arithmeticType" disabled=true style="display:none;"></select><span id="spanID' + i +'"></span></td><td class="secColumn" class="red" id="showGPA1_' + i + '"></td><td class="red" id="showGPA2_' + i +'"></td></tr>');
                $('.trLine3').after(item);
            }
        }
    }

    createHandredItems();
    makeArithOpts();

    $('.score')[0].focus();
    var getData = function () {
        var arr = new Array();
        $('.input_kc').each(function () {
            var score = $(this).find('.score').val();
            var credit = $(this).find('.credit').val();
            var name = $(this).find('.name').val();
            if (score && credit) arr.push({
                "name": name,
                "score": score,
                "credit": credit
            })
        });
        return arr;
    };
    var data_arr = getData();
    var calculate = function (type) {
        var standard_num = 0,
            standard_sum = 0,
            credit_sum = 0,
            score = 0,
            credit = 0;
        $(data_arr).each(function (i) {
            score = parseFloat($(data_arr)[i].score), credit = parseFloat($(data_arr)[i].credit);
            standard_sum += score * credit;
            credit_sum += credit
        });
        if (credit_sum == 0 || credit_sum < 0) return;
        if (type == "hundred") {
            standard_num = getnum(standard_sum * 4 / (credit_sum * 100), 2);
            dataObj ? dataObj[0].standardGPA = standard_num || 0 : 0;
            var hundredObj = arithmetic.hundred;
            var common_arr = new Array();
            for (var key in hundredObj) {
                var common_num = 0,
                    common_sum = 0;
                $(hundredObj[key]).each(function (i) {
                    var rank_arr = hundredObj[key][i].rank,
                        point = parseFloat(hundredObj[key][i].point);
                    $(data_arr).each(function (j) {
                        score = parseFloat($(data_arr)[j].score), credit = parseFloat($(data_arr)[j].credit);
                        if ((rank_arr[0] < score || score == rank_arr[0]) && (score < rank_arr[1] || score == rank_arr[1])) {
                            common_sum += credit * point
                        }
                    })
                });
                common_num = getnum(common_sum / credit_sum, 2);
                dataObj[0].commonGPA[key] = common_num || 0;
                common_num = 0
            }
        } else if (type == "five") {
            standard_num = getnum(standard_sum * 4 / (credit_sum * 5), 2);
            dataObj[1].standardGPA = standard_num || 0;
            var fiveObj = arithmetic.five;
            var common_num = 0,
                common_sum = 0;
            $(fiveObj).each(function (i) {
                var rank = fiveObj[i].rank,
                    point = fiveObj[i].point;
                $(data_arr).each(function (j) {
                    score = parseFloat($(data_arr)[j].score), credit = parseFloat($(data_arr)[j].credit);
                    if (rank == score) common_sum += credit * point
                })
            });
            common_num = getnum(common_sum / credit_sum, 2);
            dataObj[1].commonGPA = common_num || 0
        } else if (type == "rank") {
            dataObj[2].standardGPA = '--';
            var rankObj = arithmetic.rank;
            var common_num = 0,
                common_sum = 0;
            $(rankObj).each(function (i) {
                var rank = rankObj[i].rank,
                    point = rankObj[i].point;
                $(data_arr).each(function (j) {
                    score = $(data_arr)[j].score, credit = parseFloat($(data_arr)[j].credit);
                    if (rank == score) common_sum += parseFloat(credit * point)
                })
            });
            common_num = getnum(common_sum / credit_sum, 2);
            dataObj[2].commonGPA = common_num || 0
        }
        return dataObj;
    };
    var changeIntro = function () {
        var score_type = $('.scoreType').val();
        var index = $('.intro_wrapper').children().index($('.intro_' + score_type));
        $('.intro_item').eq(index).show().siblings().hide()
    };
    var init = function () {
        $('.name').attr('disabled', true).each(function () {
            $(this).attr('title', $(this).val())
        });
        $('.form_tit1_ri').trigger('click');
        changeIntro();
        $('.score')[0].focus();
        var score_type = $('.scoreType').val();
        if($('.scoreType').val() == 'hundred'){
            for(var i=1; i<8; i++){
                $('#showGPA1_' + i).html(score_type == 'rank' ? '--' : 0);
                $('#showGPA2_' + i).html('0');
            }
        }
        else{
            $('#showGPA1_1').html(score_type == 'rank' ? '--' : 0);
            $('#showGPA2_1').html('0');
        }
        $('.name').css("background", "#DDDDDD");
        reset()
    };
    init();
    $('.form_tit1_ri').click(function () {
        if ($('.name').attr('disabled')) {
            $('.name').attr('disabled', false).css({
                "background": "#fff",
                color: "#333"
            });
            $('.name')[0].select()
        } else {
            $('.name').attr('disabled', true).css({
                "background": "#DDD",
                color: "#BBB"
            });
            $('.score')[0].focus()
        }
    });
    $('#btn1').click(function (flag) {
        if (flag == 0) return;
        if($('.scoreType').val() == 'hundred'){
            $('.arithmeticType').each(function (index){
                calcHandler($('.scoreType').val(), $(this).val(), '#showGPA1_' + ($('.arithmeticType').size() - parseInt(index)), '#showGPA2_' + ($('.arithmeticType').size() - index ));
            });
        }
        else{
            calcHandler($('.scoreType').val(), $('.arithmeticType').val(), '#showGPA1_1', '#showGPA2_1');
        }
    });

    var calcHandler = function (score_type, arith_type, target1, target2) {
        data_arr = getData();
        dataObj = calculate(score_type) || [{
            standardGPA: 0,
            commonGPA: []
        }, {
            standardGPA: 0,
            commonGPA: 0
        }, {
            standardGPA: '--',
            commonGPA: 0
        }];
        var index = score_type == 'hundred' ? 0 : score_type == 'five' ? 1 : score_type == 'rank' ? 2 : 0;
        $(target1).html(dataObj[index].standardGPA);
        $('#showGPA3_2').html(dataObj[index].standardGPA);
        var commonGPA = dataObj[index].commonGPA || 0;
        if (typeof commonGPA == 'object') {
            $(target2).html(arith_type ? commonGPA[arith_type] : '0')
        } else {
            $(target2).html(commonGPA);
        }
        for(var i=1; i <=$('.arithmeticType').size(); i++){
            if($('#spanID' + i).html().indexOf('4.3') != -1){
                if($('#showGPA2_' + i).html() != 0 && $('#showGPA2_' + i).html().indexOf('/') == -1){
                    $('#showGPA2_' + i).html($('#showGPA2_' + i).html() + '/4.3');
                }
            }
            else{
                if($('#showGPA2_' + i).html() != 0 && $('#showGPA2_' + i).html().indexOf('/') == -1){
                    $('#showGPA2_' + i).html($('#showGPA2_' + i).html() + '/4');
                }
            }
        }
        if($('#showGPA3_2').html() != 0 && $('#showGPA3_2').html().indexOf('/') == -1){
            $('#showGPA3_2').html($('#showGPA3_2').html() + '/4');
        }
    }

    $('#btn2').click(function () {
        var result = window.confirm("确定要清空吗？");
        if(result){
            init();
        }
    });
    $('.intro_top_ri').click(function () {
        $('.intro_wrapper').slideToggle(1000, function () {
            var title = $('.icon_zk').attr('title');
            if (title == '展开') {
                $('.icon_zk').attr('title', '收起').attr("src", "images/icon_sq.gif")
            } else {
                $('.icon_zk').attr('title', '展开').attr("src", "images/icon_zk.gif")
            }
        })
    });
    $('.scoreType,.arithmeticType').change(function () {
        if ($(this).hasClass('arithmeticType')) {
            // $('#btn1').trigger('click')
        } else if ($(this).hasClass('scoreType')) {
            makeArithOpts();
            reset();
            var score_type = $('.scoreType').val();
            if (score_type == "rank") {
                $('#showGPA1_1').html("--")
            } else {
                for(var i=1; i<=$('.trLine').size(); i++){
                    $('#showGPA1_' + i).html(0);
                    $('#showGPA2_' + i).html(0);
                    $('#showGPA3_2').html(0);
                }
            }
            if($('.scoreType').val() != 'hundred'){
                var loopNum = $('.arithmeticType').size();
                for(var i=2 ; i<=loopNum; i++){
                    $('#showGPA1_' + i).parent().remove();
                }
            }
        }
        changeIntro();
        dataObj = calculate(score_type)
    });
    var validate = function (str, target) {
        var str = $.trim(str);
        var reg;
        if (target.hasClass('score')) {
            var score_type = $('.scoreType').val();
            reg = score_type == 'hundred' ? /^([\d]{1,2}|[\d]]+[\.][\d]{1,2}|100|100.0|100.00)$/g : score_type == 'five' ? /^([0-5]{1}|[2-4]+[\.][\d]{1,2}|5.0|5.00)$/g : /[A,A\+,A\-,B,B\+,B\-,C,C\+,C\-,D,D\+,D\-,F]{1,2}$/g
        } else if (target.hasClass('credit')) {
            var credit = $.trim(target.val());
            var reg2 = /[\d]$/g;
            if (!reg2.test(credit) && credit) {
                $(target).val('信息输入错误').css("color", "red").click(function () {
                    if ($(target).val() == '信息输入错误') $(target).css("color", "#333").val('')
                })
            }
            return;
        } else if (target.hasClass('name')) {
            str = str.substring(0, 20);
            target.val(str).attr('title', str);
            return
        }
        if (!reg.test(str) && str) {
            $(target).val('信息输入错误').css("color", "red").click(function () {
                if ($(target).val() == '信息输入错误') $(target).css("color", "#333").val('')
            })
        }
    };
    $('.name,.score,.credit').blur(function () {
        validate($(this).val(), $(this));
        // $('#btn1').trigger('click')
    });
    var scrollTop = $(document).scrollTop();
    $('#add_item').click(function (e) {
        var index = $('.input_kc').size() + 1;
        var disabled = $($('.name')[0]).attr('disabled');
        var style = $($('.name')[0]).attr('style');
        var txtnum = $('#txtNum').val();
        if (index > 100) {
            alert('您已经增加了100个录入项！');
            e.preventDefault();
            return;
        }
        for(var i = 0; i < txtnum; i++){
            var item = $('<div class="input_kc"><input type="text" value="课程' + index + '" name="" id="name' + index + '" class="input_txt name"><input type="text" name="" id="score' + index + '" class="input_txt score"><input type="text" name="" id="credit' + index + '" class="input_txt credit"><div class="clear"></div></div>');
            $('.form_add').before(item);
            item.find('.name').attr({
                'disabled': disabled,
                "style": style ? style : ""
            }).select();
            scrollTop += 38;
            $(document).scrollTop(scrollTop);
            index++;
        }
        $('#txtNum').val('1');
        $('.name,.score,.credit').blur(function () {
            validate($(this).val(), $(this));
            // $('#btn1').trigger('click')
        });
        e.preventDefault()
    });
    //    updata by xiaoyan  //
    $('#btn1').click(function() {    //  计算，弹窗显示

        var showBox = $('<div id="showBox">');
        var mask = $('<div id="mask">');
        var ad = $('<div id="ad">').css({width: '100%'});
        var close = $('<spam id="close">').html('×');
        ad.html('<a href="http://g.chasedream.com/ChaseDream/Service/program" target="_blank">算出GPA之后该做什么？欢迎了解ChaseDream的各种留学申请利器！</a>')
        mask.css('height', $(document).height());

        $('body').append(mask, showBox);
        showBox.html($('.main_ri').html());
        showBox.find('.intro').remove();
        showBox.append(close, ad);

        showBox.css({
            left: ($(window).width() - showBox.width()) / 2 + $(document).scrollLeft(),
            top: '80px'  // ($(window).height() - showBox.height()) / 2 + $(document).scrollTop()
        });
        if ($(window).height() <= showBox.height()) {
            showBox.css('top',0);
        }
        if ($(window).width() <= showBox.width()) {
            showBox.css('left',0);
        }
        close.click(function () {     //  关闭窗口
            showBox.remove();
            mask.remove();
        })
        $(window).resize(function () {     //   窗口改变大小里窗口位置保持居中
            showBox.css({
                left: ($(window).width() - showBox.width()) / 2 + $(document).scrollLeft(),
                top: '80px'    //($(window).height() - showBox.height()) / 2 + $(document).scrollTop()
            });
            mask.css('height', $(document).height());
            if ($(window).height() <= showBox.height()) {
                showBox.css('top',0);
            }
            if ($(window).width() <= showBox.width()) {
                showBox.css('left',0);
            }
        })
    })

});