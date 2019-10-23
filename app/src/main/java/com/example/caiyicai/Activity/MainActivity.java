package com.example.caiyicai.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.Face3DAngle;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.VersionInfo;
import com.example.caiyicai.Arcface.camera.CameraHelper;
import com.example.caiyicai.Arcface.camera.CameraListener;
import com.example.caiyicai.Arcface.common.Constants;
import com.example.caiyicai.Arcface.model.DrawInfo;
import com.example.caiyicai.Arcface.util.ConfigUtil;
import com.example.caiyicai.Arcface.util.DrawHelper;
import com.example.caiyicai.Arcface.widget.FaceRectView;
import com.example.caiyicai.R;
import com.example.caiyicai.util.AlertDialogUtils;
import com.example.caiyicai.util.GlideImageLoader;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.robotemi.sdk.constants.SdkConstants.EXTRA_NLP_RESULT;

public class MainActivity extends AppCompatActivity implements OnRobotReadyListener, Robot.NlpListener, OnGoToLocationStatusChangedListener , Robot.TtsListener , ViewTreeObserver.OnGlobalLayoutListener{

    private Robot robot;
    private Banner banner;
    List<Integer> path;
    private TtsRequest pendingTts;
    private String[] introduce={
            "各位朋友，大家好，欢迎您来到位于佛山南庄镇核心商务区的材易采数字体验中心。佛山南庄作为中国南方陶瓷重镇，一直被誉为中国建陶第一镇、中国陶瓷商贸之都。我们现在所处的具体位置是佛山中国陶瓷总部基地陶瓷剧场，这里东接佛山CBD、绿岛湖创新产业区，中国陶瓷城，石湾陶瓷卫浴世界，背靠南庄最大陶瓷生产片区。就让我来带大家参观一下我们的材易采数字体验中心吧，请跟我来。",
            "平台简介墙展示了我们中陶城在建陶行业17年的深厚积淀及孵化了无数优秀陶瓷品牌的历程，我们将产业互联网和传统的陶瓷行业深度结合。中陶城集团公司目前致力成为全球化泛家居行业营销、会展、服务运营商。所以我们与全国90%优质的陶瓷卫浴企业一直保持良好密切的合作关系。另外，互联网家装、整装浪潮不断侵蚀终端市场。现在的终端门店，看不到人了，因为都去找整装了。这也意味着线下业务必须转型。我们也正在朝这方面发展。接下来参观核心业务展示区。",
            "首先，我们为平台合作伙伴提供一站式工程服务体系。从工程报备、集采议价、选样寄样、工程产品定制，到后期产品双重质检、物流匹配、金融服务，材易采遵循“专、快、全、省、优”的服务标准，为大家提供完善的后续服务。记住，在材易采你能得到一流的品牌、一线的产品、一线的工厂、一手的价格、一流的服务。接下来参观我们的数据体验中心，快跟我来。",
            "这里是数据体验中心，在我左边是和日智能全屋智能控制方案，通过市面的各类语音音响来控制全屋大小家电、窗帘，灯光等。右边是浪鲸卫浴展示区，他们家的小便器及水疗马桶是开发非接触式肠道水疗，真正实现从“水洗”到“水疗”的历史性跨越，这里还有浪鲸的产品，请跟我来。",
            "这里是卫浴产品展示区，展示品牌有东鹏、浪鲸、箭牌以及美加华，一般都是浴室柜、马桶、淋浴等产品设施。居家环境基本配置一般都是浴室柜、马桶、淋浴等。但在房价不断高涨的今天，如何在小空间实现更多的功能是很多顾客的隐形需求。大家可以参观一下，那么接下来让我带各位到建筑外墙砖展示区看看，请跟我来。",
            "现在到达的是瓷砖区域，首先这里是外墙砖区域，常见的种类有釉面别墅外墙砖、通体别墅外墙砖、外墙马赛克。外墙砖在环境污染比较大、空气灰尘多的地区，无疑具有非常大的优势。具备很好的耐久性和质感，并具有易清洗、防火、抗水、耐磨、耐腐蚀和维护费用低，经过多年使用仍能保持较好质感。请跟我来。",
            "这里是居住空间区，以前一般是一个空间就同一款砖，现在都会通过两种不同花色、纹理，或者瓷砖+木垫板等进行拼贴，令空间时尚多变，对面是商业空间区，在产品选择上，商业空间将更加倾向能更体现品质，营造文化氛围的产品，请跟我来。",
            "这里是公共设备空间，我们利用了这种推拉架的设计，展示产品材质、花色、细节等效果，对面是乡村建设区，我们也有很多不错的产品，右边的数字体验区，我们在未来每一个门店都会配备的材易采数字一体机，让客户能够自由的采购产品。",
            "材易采数据呈现中心呈现的是材易采的线上运营大数据动态报表，包括品牌关注度、报备排行、上架情况、用户行为分析数据、物流数据等，数据都是实时更新的，在正中央的是全国地图，展示每个省份的相应的数据，包括合伙人收益统计等。以上就是我们整个展厅的介绍,拜拜。",
    };
    private String[] location={"入口","平台简介墙","材易采核心业务展示区","数据体验中心","卫浴产品展示区","建筑外墙展示区","居住空间区","公共设备空间","数据呈现中心"};
    private int introduceNo=0;
    boolean speakCode=false;
    AlertDialogUtils utils;


    /**
     * 主动迎宾
     */
    private static final String TAG = "PreviewActivity";
    private CameraHelper cameraHelper;
    private DrawHelper drawHelper;
    private Camera.Size previewSize;
    private Integer rgbCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private FaceEngine faceEngine;
    private int afCode = -1;
    private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;
    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;
    private FaceRectView faceRectView;

    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    private boolean ifSpeak=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        robot=Robot.getInstance();//robot对象
        setBanner();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(attributes);
        }

        // Activity启动后就锁定为启动时的方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        previewView = findViewById(R.id.texture_preview);
        faceRectView = findViewById(R.id.face_rect_view);
        //在布局结束后才做初始化操作
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }


    //设置轮播图
    public void setBanner(){
        banner=findViewById(R.id.banner);
        path=new ArrayList<>();
        path.add(R.drawable.banner1);
        path.add(R.drawable.banner2);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(path);
        //banner设置方法全部调用完毕时最后调用
        banner.setDelayTime(4000);
        banner.start();
    }


    //获取全部位置
    private List<String> getLocation(){
        return robot.getLocations();
    }

    //前往指定位置
    private void goTo(String palace){
        Robot robot = Robot.getInstance();
        robot.goTo(palace);
    }


    //前往回调
    @Override
    public void onGoToLocationStatusChanged(String s, String s1) {
        Log.d("位置信息","位置状态"+s1);
        switch (s1){
            case "complete":
                if (introduceNo!=9){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(100);
                                say(introduce[introduceNo]);
                                introduceNo++;
                                Log.d("位置信息", "introduceNo  " + introduceNo);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                if (introduceNo==9){
                    Intent k = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(getBaseContext().getPackageName());
                    k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(k);
//                    introduceNo=0;
                }
                break;
        }
    }

    //说话回调
    @Override
    public void onTtsStatusChanged(TtsRequest ttsRequest) {
        Log.d("位置信息","介绍状态"+ttsRequest.getStatus());
        switch (ttsRequest.getStatus()){
            case COMPLETED:
                if (speakCode==true){
                    if (introduceNo==9){
                        speakCode=false;
                        goTo("入口");
                    }else {
                        goTo(location[introduceNo]);
                    }
                }
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        NlpResult nlpResult = getIntent().getParcelableExtra(EXTRA_NLP_RESULT);
        if (nlpResult != null) {
            onNlpCompleted(nlpResult);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册监听
        robot.addOnRobotReadyListener(this);
        robot.addNlpListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addTtsListener(this);
    }

    @Override
    protected void onPause() {
        //取消监听
        robot.removeOnRobotReadyListener(this);
        robot.removeNlpListener(this);
        robot.removeOnGoToLocationStatusChangedListener(this);
        robot.removeTtsListener(this);
        super.onPause();
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

    @Override
    public void onRobotReady(boolean b) {
        if (b){
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                Robot.getInstance().onStart(activityInfo);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onNlpCompleted(NlpResult nlpResult) {
        switch (nlpResult.action){
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



    //获取权限
    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this.getApplicationContext(), neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                activeEngine();
                initCamera();
                if (cameraHelper != null) {
                    cameraHelper.start();
                }
            } else {
                Toast.makeText(this.getApplicationContext(), "权限请求失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 激活引擎
     */
    public void activeEngine() {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(MainActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            initEngine();
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            if (cameraHelper != null) {
                                //启动相机
                                cameraHelper.start();
                            }
                            Log.e("引擎已激活", "引擎已激活，无需再次激活");
                        } else {
                            Toast.makeText(MainActivity.this, "激活引擎失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //初始化引擎
    private void initEngine() {
        faceEngine = new FaceEngine();
        afCode = faceEngine.init(this, FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, 20, FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS);
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        Log.i(TAG, "initEngine:  init: " + afCode + "  version:" + versionInfo);
        if (afCode != ErrorInfo.MOK) {
            Toast.makeText(this, "引擎初始化失败:"+afCode, Toast.LENGTH_SHORT).show();
        }
    }


    //初始化相机
    private void initCamera() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Log.i(TAG, "onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                        , cameraId, isMirror,false,false);
            }


            @Override
            public void onPreview(byte[] nv21, Camera camera) {

                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                List<FaceInfo> faceInfoList = new ArrayList<>();
                int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
                    code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
                    if (code != ErrorInfo.MOK) {
                        return;
                    }
                }else {
                    return;
                }

                List<AgeInfo> ageInfoList = new ArrayList<>();
                List<GenderInfo> genderInfoList = new ArrayList<>();
                List<Face3DAngle> face3DAngleList = new ArrayList<>();
                List<LivenessInfo> faceLivenessInfoList = new ArrayList<>();
                int ageCode = faceEngine.getAge(ageInfoList);
                int genderCode = faceEngine.getGender(genderInfoList);
                int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
                int livenessCode = faceEngine.getLiveness(faceLivenessInfoList);

                //有其中一个的错误码不为0，return
                if ((ageCode | genderCode | face3DAngleCode | livenessCode) != ErrorInfo.MOK) {
                    return;
                }
                if (faceRectView != null && drawHelper != null) {
                    List<DrawInfo> drawInfoList = new ArrayList<>();
                    for (int i = 0; i < faceInfoList.size(); i++) {
                        drawInfoList.add(new DrawInfo(drawHelper.adjustRect(faceInfoList.get(i).getRect()), genderInfoList.get(i).getGender(), ageInfoList.get(i).getAge(), faceLivenessInfoList.get(i).getLiveness(), null));
                    }
                    drawHelper.draw(faceRectView, drawInfoList);


                    //检测到人脸
                    if (drawInfoList.size()>0&&ifSpeak==true){
                        speakCode=false;
                        ifSpeak=false;
                        TtsRequest ttsRequest = TtsRequest.create("您好，欢迎来到第34届陶博会中国陶瓷城展馆，有什么可以帮您的？", false);
                        mTimer.start();
                        if (robot.isReady()){
                            Robot.getInstance().speak(ttsRequest);
                            pendingTts=null;
                        }else{
                            pendingTts=ttsRequest;
                        }

                        utils = AlertDialogUtils.getInstance();
                        utils.showConfirmDialog(MainActivity.this, "您好，欢迎来到第34届陶博会中国陶瓷城展馆，有什么可以帮您的？");
                        //按钮点击监听
                        utils.setOnButtonClickListener(new AlertDialogUtils.OnButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(AlertDialog dialog) {
                                mTimer.cancel();
                                startActivity(new Intent(MainActivity.this,QuestionsActivity.class));
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegativeButtonClick(AlertDialog dialog) {
                                mTimer.cancel();
                                speakCode=true;
                                say("请跟我来");
                                for (int i = 0; i < getLocation().size(); i++) {
                                    Log.d("allLocation","全部位置 "+getLocation().get(i));
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (drawHelper != null) {
                    drawHelper.setCameraDisplayOrientation(displayOrientation);
                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };
        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(previewView.getMeasuredWidth(),previewView.getMeasuredHeight()))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(rgbCameraId != null ? rgbCameraId : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
        cameraHelper.start();
    }


    /** 倒计时10秒，一次1秒 *///倒计时控制迎宾频率
    CountDownTimer mTimer = new CountDownTimer(10 * 1000, 1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            Log.d("onFinish","onFinish");
            utils.closeDialog();
            ifSpeak = true;
        }
    };

    /**
     * 在{@link #previewView}第一次布局完成后，去除该监听，并且进行引擎和相机的初始化
     */
    @Override
    public void onGlobalLayout() {
        previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        } else {
            initEngine();
            initCamera();
        }
    }

    private void unInitEngine() {

        if (afCode == 0) {
            afCode = faceEngine.unInit();
            Log.i(TAG, "unInitEngine: " + afCode);
        }
    }

    @Override
    protected void onDestroy() {
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        unInitEngine();
        super.onDestroy();
    }


}
