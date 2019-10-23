package com.example.caiyicai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caiyicai.R;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{

    List<String> list;
    Context context;
    private Robot robot=Robot.getInstance();
    private TtsRequest pendingTts;

    public QuestionAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_questions,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.item_question.setText(list.get(i));
        viewHolder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                say(list.get(i));
                if(list.get(i).equals("你叫什么名字呀？")){
                    explain("q1");
                }
                if (list.get(i).equals("有什么活动？")){
                    explain("q2");
                }
                if (list.get(i).equals("新用户如何注册？")){
                    explain("q3");
                }
                if (list.get(i).equals("扫地机如何领取？")){
                    explain("q4");
                }
                if (list.get(i).equals("会员有什么权益？")){
                    explain("q5");
                }
                if (list.get(i).equals("你们展位是做什么的？")){
                    explain("q6");
                }
                if (list.get(i).equals("材易采的核心价值是什么？")){
                    explain("q7");
                }
                if (list.get(i).equals("材易采核心业务是什么？")){
                    explain("q8");
                }
                if (list.get(i).equals("你们合作的品牌有哪些？")){
                    explain("q9");
                }
                if (list.get(i).equals("材易采小程序能做什么？")){
                    explain("q10");
                }
                if (list.get(i).equals("你们公司什么时候成立？")){
                    explain("q11");
                }
                if (list.get(i).equals("你们以后的发展方向是什么？")){
                    explain("q12");
                }
                if (list.get(i).equals("城市合伙人权益有哪些？")){
                    explain("q13");
                }
                if (list.get(i).equals("今年陶博会有什么亮点可看？")){
                    explain("q14");
                }
                if (list.get(i).equals("陶博会有什么活动？")){
                    explain("q15");
                }
                if (list.get(i).equals("中国陶瓷城馆在哪里？")){
                    explain("q16");
                }
                if (list.get(i).equals("陶瓷城馆有哪些参展商？")){
                    explain("q18");
                }
                if (list.get(i).equals("陶瓷城里面有哪些固定品牌？")){
                    explain("q18");
                }
                if (list.get(i).equals("中国陶瓷总部基地在哪里？")){
                    explain("q19");
                }
                if (list.get(i).equals("总部基地有什么看？")){
                    explain("q20");
                }
                if (list.get(i).equals("总部基地有哪些参展商？")){
                    explain("q21");
                }
                if (list.get(i).equals("总部基地里面有哪些固定品牌？")){
                    explain("q22");
                }
                if (list.get(i).equals("佛山会展中心在哪里？")){
                    explain("q23");
                }
                if (list.get(i).equals("会展中心有什么看？")){
                    explain("q24");
                }
                if (list.get(i).equals("会展中心有哪些参展商？")){
                    explain("q25");
                }
                if (list.get(i).equals("附近有好吃的吗？")){
                    explain("q26");
                }
                if (list.get(i).equals("我要订物流货运")){
                    explain("q27");
                }
                if (list.get(i).equals("附近有什么酒店？")){
                    explain("q27");
                }
                if (list.get(i).equals("展会附近哪里有汽车站？")){
                    explain("q28");
                }
                if (list.get(i).equals("火车站在哪里？")){
                    explain("q29");
                }
                if (list.get(i).equals("这里有前往广州的穿梭车吗？")){
                    explain("q30");
                }
                if (list.get(i).equals("最近的候机楼位置在哪里？")){
                    explain("q31");
                }
                if (list.get(i).equals("距离展会最近的地铁站在哪里？")){
                    explain("q32");
                }
                if (list.get(i).equals("有自动柜员机吗？")){
                    explain("q33");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_question;
        View thisView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thisView=itemView;
            item_question=itemView.findViewById(R.id.item_question);
        }
    }

    //说话
    public void say(String word){
        TtsRequest ttsRequest = TtsRequest.create(word, true);
        if (robot.isReady()){
            Robot.getInstance().speak(ttsRequest);
            pendingTts=null;
        }else{
            pendingTts=ttsRequest;
        }
    }

    public void explain(String code) {
        switch (code){
            case "q1":
                say("您好，我是来自材易采的小采，今年两岁，感谢您光临第34届陶博会中国陶瓷城展馆（ 佛山国际会议展览中心、中国陶瓷总部基地），活动推荐：材易采599VIP会员活动，陶博会期间加入材易采，立送价值999元AI智能扫地机一台，限量1000台！");
                break;
            case "q2":
                say("34届陶博会期间加入材易采599VIP会员，不仅可以一部手机轻松做工程！前1000名还送千元智能扫地机一台！升级成为材易采城市合伙人，再送2万元工程抵用券！");
                break;
            case "q3":
                say("扫描材易采APP码/小程序码，绑定手机号码，即可注册成功 599VIP会员注册：注册新用户成功后，在线申请成为599VIP会员。");
                break;
            case "q4":
                say("注册新用户成功后，可在线申请成为599VIP会员。打开材易采APP或小程序，点击“我的”/599VIP/城市合伙人签到领礼物");
                break;
            case "q5":
                say("599VIP会员是拥有150+建陶品牌工程代理权，全国海量工程招采信息，一站式工程集采服务，超性价原装进口大板代理权，严选爆款工程集采价采购权等更多权益");
                break;
            case "q6":
                say("材易采是由佛山中国陶瓷城集团全资建立的子公司，成立于2017年，是一家专注于全球建材家居领域的S2B工程集采服务平台。材易采以“产业互联网思维”为指导，依托集团公司强大的资源优势，当前致力于整合上游供应链体系，为全球的中小工程买家提供专业、快速、精准、高效、优质的全方位采购服务。两年间，我们已经拥有：300多家合作品牌，10000多家线上SKU，200多个城市合伙人，100个成功运营工程项目等");
                break;
            case "q7":
                say("材易采，中国陶瓷卫浴全球工程集采平台，让您一部手机轻松做工程！拥有海量产业供应链资源，提供一站式工程集采服务，材易采的目标是，让天下没有难做的工程！");
                break;
            case "q8":
                say("材易采拥有海量产业供应链资源，百分之90以上一二线品牌供应链，海量高性价比产品资源，自有品牌精品、爆品，为工程买家提供一站式工程集采服务，售前匹配、报备、选寄样、设计、中投标、议价、合同、金融、物流、后服务、客诉处理、质量追溯。真正做到一部手机轻松做工程！");
                break;
            case "q9":
                say("一二线知名品牌供应如：东鹏、鹰牌、马可波罗、新中源、金意陶等多家企业");
                break;
            case "q10":
                say("材易采APP、小程序，功能齐全，集齐了找产品、找品牌、工程报备、供求发布、工程项目推荐等多项关于工程集采服务，做到一部手机轻松做工程");
                break;
            case "q11":
                say("材易采2017年成立，迄今为止已有300多家合作品牌，10000多家线上SKU，200多个城市合伙人，100个成功运营工程项目等。");
                break;
            case "q12":
                say("2020年，材易采将在全球招募800个的城市合伙人，建立50个城市运营中心，将在全国分点建立24小时线下数字选材中心。接入线上海量产品数据库，提供线下下单交易、VR选材、在线设计体验等服务，引入附近经销商资源进驻，让陶瓷卫浴工程打破时空限制，开拓线上线下立体式全覆盖销售渠道。材易采目标：致力整合金融、物流成熟资源，打造陶瓷卫浴行业最大的工程服务平台；并于未来五年打造建材、家居行业最大工程服务平台。");
                break;
            case "q13":
                say("海量产业供应链资源，打通百分之90一二线品牌供应链,海量高性价比产品资源，平台自有品牌精品、爆品，一站式工程集采服务 —售前匹配、报备、选寄样、设计、售中投标、议价、合同、金融、物流、售后服务、客诉处理、质量追溯，一部手机轻松做工程一站式智能工程业务系统，一个个人会员管理系统");
                break;
            case "q14":
                say("第34届佛山陶博会由中国陶瓷城展馆、中国陶瓷总部展馆、佛山国际会议展览中心展馆三大展馆组成，展示规模48万㎡，涵盖东鹏、鹰牌、蒙娜丽莎、金意陶、希恩、高仪、诺贝尔、宏宇、大角鹿、欧文莱、博德、道格拉斯、ICC、TOTO、Slender、Kohler、Grohe,BRAVAT ，Hansgrohe等800多个国内外企业参展。");
                break;
            case "q15":
                say("第34届佛山陶博会由中国陶瓷城展馆、中国陶瓷总部展馆、佛山国际会议展览中心展馆三大展馆组成，展示规模48万㎡，涵盖东鹏、鹰牌、蒙娜丽莎、金意陶、希恩、高仪、诺贝尔、宏宇、大角鹿、欧文莱、博德、道格拉斯、ICC、TOTO、Slender、Kohler、Grohe,BRAVAT ，Hansgrohe等800+国内外企业参展。 ");
                break;
            case "q16":
                say("中国陶瓷城展馆——品牌馆，地址：广东省佛山市禅城区江湾三路2号");
                break;
            case "q17":
                say("传统的仿古砖、大理石瓷砖、木纹砖、抛釉砖等产品展示，本届多了陶瓷大板、岩板、陶瓷厚板以及马赛克、花砖、智能卫浴等产品展出。");
                break;
            case "q18":
                say("主要参展商有希恩、高仪、科勒、东鹏、诺贝尔、Slender、USCER、ICC、TOTO、 Panasonic Space、Kohler、BOBO、理想、浴+居、心海伽蓝、道格拉斯、金意陶、宏宇、大角鹿、慕瓷、贝利泰、陶艺轩、国星、芙洛尼、豪山、pacaya、正之联、Nautilus 纳缇雅斯、Perseus、Prime Horses等国内外优秀品牌，以及全国各产区各类自主出口品牌。");
                break;
            case "q19":
                say("中国陶瓷总部展馆-工程馆，地址：广东省佛山市禅城区季华西路68号");
                break;
            case "q20":
                say("本届工程产品馆再升级，全线展示具有创新力与竞争力的工程类产品以及应用案例，东鹏、鹰牌2086、蒙娜丽莎、绿能新材、萨米特、博德、三荣企业、瓦莎齐、绘意石材、康建家居、百家好事等优秀品牌强势加入，主要展示绿能板、特色水磨石、陶瓷大板、发泡陶瓷、防滑砖、瓷抛石、生态石、岩板等创新产品。");
                break;
            case "q21":
                say("本届工程产品馆再升级，全线展示具有创新力与竞争力的工程类产品以及应用案例，东鹏、鹰牌2086、蒙娜丽莎、绿能新材、萨米特、博德、三荣企业、瓦莎齐、绘意石材、康建家居、百家好事等优秀品牌强势加入，主要展示绿能板、特色水磨石、陶瓷大板、发泡陶瓷、防滑砖、瓷抛石、生态石、岩板等创新产品。");
                break;
            case "q22":
                say("总部展厅主要有白兔陶瓷、亚细亚、ICC、欧文莱、兴辉国际、卓远陶瓷、博德、华鹏陶瓷、惠达、浪鲸、台湾罗马、格仕陶、芒果、HBI、琟璟家居、90°家居、嘉俊、大唐合盛、BOBO、玛拉兹等知名企业。");
                break;
            case "q23":
                say("佛山国际会议展览中心展馆—新锐品牌馆，地址：广东省佛山市禅城区南庄镇陶博大道");
                break;
            case "q24":
                say("本届展会新设特色产品区、辅材专馆，QD瓷砖、大角鹿、伊莉莎白、御家、宝云石业、宣言、K-GRESS、伊元素、装象、酷家乐、三维家、亿固、九木新材、聚享展架等一众实力品牌强势参展。除了展示传统的大理石瓷砖、仿古砖、马赛克、岩板等产品外，本届还有家具建材软件配套等产品展出。");
                break;
            case "q25":
                say("本届展会新设特色产品区、辅材专馆，QD瓷砖、大角鹿、伊莉莎白、御家、宝云石业、宣言、K-GRESS、伊元素、装象、酷家乐、三维家、亿固、九木新材、聚享展架等一众实力品牌强势参展。除了展示传统的大理石瓷砖、仿古砖、马赛克、岩板等产品外，本届还有家具建材软件配套等产品展出。");
                break;
            case "q26":
                say("中国陶瓷城展馆：2楼高尔思咖啡、1楼麦当劳、1楼7-11便利店；中国陶瓷总部展馆：员工饭堂、沙县小吃、悠悦甜品、衡阳原味鱼粉、许久蛋糕、7-ELEVEN、糖黏豆、香港满芬、东时便当、润一润甜品美食、QC cafe、LA~BOBO、重庆三碗面、粮馫饺子馆、宴福酒楼、H BAR、宜宾燃面；佛山国际会议展览中心展馆：森木马来西亚简餐、一些柠檬一些茶、吉姆大师傅、宴福酒楼");
                break;
            case "q27":
                say("请前往中国陶瓷城东门大堂1楼展会服务中心或者中国陶瓷总部陶瓷剧场一楼买家服务中心、华夏馆的买家服务中心。");
                break;
            case "q28":
                say("石湾汽车站位于江湾二路，在中国陶瓷城对面，走路约5分钟；鸿运汽车站位于汾江中路。");
                break;
            case "q29":
                say("佛山火车站位于文昌路，可在中国陶瓷城东门对面公交车站搭乘101/109到达或者乘坐的士前往；佛山西站位于佛山罗村，可在中国陶瓷城北门公交车站搭乘K3路到达前往。");
                break;
            case "q30":
                say("这里提供前往中国大酒店、琶洲的免费穿梭车。");
                break;
            case "q31":
                say("佛山候机楼在魁奇路，可搭乘的士大约15分钟。");
                break;
            case "q32":
                say("最近的地铁站在季华园，叫季华园地铁站，搭乘的士大约十五到二十分钟，如需搭公交车，在中国陶瓷城榴苑路坐154或者中国陶瓷总部乘坐133路公交车可以到达。");
                break;
            case "q33":
                say("中国陶瓷城1楼：中国工商银行、中国邮政、中国银行、 中国农业银行。中国陶瓷总部西区F01：佛山农商银行。佛山国际会议展览中心展馆：中国工商银行、佛山农商银行、中国银行");
                break;
        }
    }

}
