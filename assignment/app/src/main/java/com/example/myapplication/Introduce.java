package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Script;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Introduce extends AppCompatActivity {

    TextView playintro ,playintro2,playintro3,playintro4,playintro5;
    Button usbu, jpbu, hkbu, twbu,back,home,people;
    String HkLink,TwLink,UsaLink,JpLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        playintro2 = findViewById(R.id.playintro2);
        playintro2.setMovementMethod(LinkMovementMethod.getInstance());

        playintro3 = findViewById(R.id.playintro3);
        playintro3.setMovementMethod(LinkMovementMethod.getInstance());

        playintro4 = findViewById(R.id.playintro4);
        playintro4.setMovementMethod(LinkMovementMethod.getInstance());

        playintro5 = findViewById(R.id.playintro5);
        playintro5.setMovementMethod(LinkMovementMethod.getInstance());

        playintro = findViewById(R.id.playintro);
        usbu = findViewById(R.id.usbu);
        jpbu = findViewById(R.id.jpbu);
        hkbu = findViewById(R.id.hkbu);
        twbu = findViewById(R.id.twbu);
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        people = findViewById(R.id.people);

        UsaLink = "<a href='https://zh.wikipedia.org/wiki/%E7%BE%8E%E5%9C%8B%E9%BA%BB%E5%B0%87%22%3E'>美國麻將的玩法詳細請按此</a>";
        JpLink = "<a href='https://zh.wikipedia.org/zh-hk/%E6%97%A5%E6%9C%AC%E9%BA%BB%E9%9B%80%22%3E'>日本麻雀的玩法詳細請按此</a>";
        HkLink = "<a href='https://zh.wikipedia.org/wiki/%E9%A6%99%E6%B8%AF%E9%BA%BB%E9%9B%80%22%3E'>香港麻將的玩法詳細請按此</a>";
        TwLink = "<a href='https://zh.wikipedia.org/wiki/%E5%8F%B0%E7%81%A3%E9%BA%BB%E5%B0%87%22%3E'>台灣麻雀的玩法詳細請按此</a>";


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Introduce.this,RealSecondPage.class);
                startActivity(i);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Introduce.this,MainActivity.class);
                startActivity(i);
            }
        });

        people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Introduce.this,setting.class);
                startActivity(i);
            }
        });
        usbu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                SpannableStringBuilder ssb = new SpannableStringBuilder();
                ssb.append("起源及介紹 \n" +
                        " 簡稱 Maahj ,美國於1937年成立的全美國麻雀聯盟,每年都會發表正式規則,也會變更標準食糊牌型。美國麻將特色是牌的左上有數字字母,甚至乎沒有「中」「發」呢兩個中文字，而會用各種顏色的龍代替中文。 \n\n" +
                        " 麻雀介紹 \n" +
                        " 一副數目:總共152張 \n" +
                        "筒子（dots）: 一筒至九筒，每款各4張，共36張。 \n" +
                        "索子（bamboos, bams）: 一索至九索，每款各4張，共36張。\n" +
                        "萬子（characters, craks）: 一萬至九萬，每款各4張，共36張。 \n" +
                        "風牌（winds）: 東、南、西、北4款，每款各4張，共16張。 \n" +
                        "三元牌（dragons）: 紅中(red dragon) ,青發(green dragon),白板(white dragon，別稱soap),每款各4張，共12張。 \n" +
                        "花牌（flower）: 有兩組，每組4款，每款1張，共8張。Spring(春)、 Summer(夏)、Autumn(秋)、Winter(冬)Plum.B(梅)、Orchid(蘭)、Chrys.(菊)、Bamboo(竹) \n" +
                        "百搭牌（joker）:又稱鬼牌、小丑、萬用牌，共8張。 \n\n" +
                        "麻雀玩法/規則說明 \n" +
                        "每盤開始之前，四個玩家就會開始「Charleston」（即換牌）。一次換牌合共要換三次，第一次傳牌給下家（並從上家拿到牌）、第二次和對家互換、第三次和上家互換。 在第一次換牌完成以後，如果四家同意，可以再做一次換牌，重複上面的三套換牌程序。除此之外，在這任何一次換牌完成，你和對家還可以另外再換一、二、或者三枚。 \n" +
                        "(一家有最多七次機會換牌，可以將自己成手十三隻全部換走曬。) \n" +
                        "手牌通常都會放在架子上，叫牌而亮出的牌型必須擺在架子的上方。 \n"+
                        "面子 \n" +
                        "而面子的組成為單張（single），對（pair）、碰（刻子、pung）、槓（kong）、昆（quint）、6張一組（sextet），沒有順子（chow）。 \n" +
                        "百搭牌 \n" +
                        "百搭牌不能在交換手牌時傳給他家。 擁有3張以上時可作為單純的面子，在遊戲中被丟出的百搭牌會被認作上次出的牌。 \n"
                );
                playintro.setText(ssb);
                playintro2.setVisibility(View.INVISIBLE);
                playintro3.setVisibility(View.INVISIBLE);
                playintro4.setVisibility(View.INVISIBLE);
                playintro5.setVisibility(View.VISIBLE);
            }
        });

        jpbu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                SpannableStringBuilder ssb = new SpannableStringBuilder();

                ssb.append("起源及介紹\n"  +
                        "日本麻雀（日語：麻雀（マージャン）），是日本常見的麻雀玩法，通常以點計算分數，而點數又決定了最終的輸贏。\n" +
                        "一般而言，日本麻雀專用的麻雀牌用色較比其他地區常見的麻雀牌單調。其他地區的藍色，在日本麻雀牌裏通常都會深得變成黑色，日本麻雀牌的綠色也暗得跟它分別不太大。筒子僅黑色與紅色兩色，索子僅綠色與紅色兩色，漢字使用行書體寫成（如萬子牌、風牌與三元牌），與一般麻雀牌的楷書體不同，而白板則是全白的番子。\n" +
                        "日本麻雀，通常使用34種共136枚牌的麻雀牌。在較為流行的規則上，也有不少的地方規則。 目前日本可以進行日本麻雀遊戲的地方，已經不再限於專用的麻雀館或家中，在遊戲機中心或電腦也能簡單的找到相應的電子遊戲。\n\n"  +
                        "玩法/規則說明\n"  +
                        "日本麻雀一般由四人進行，但也有三人麻雀或兩人麻雀。遊戲進行時，每名玩家眼擁有13張牌，稱作「手牌」。玩家按照順序從「山牌」中摸一張牌再打出一張牌。玩家的目的是經過若干次摸打之後，使自己的13張牌與自己的自摸牌或別人的棄牌組成特定的牌型，稱作「食糊」。食糊後，眼根據牌型的不同進行籌碼的授受。遊戲結束時擁有最多籌碼的玩家，為遊戲的勝者。\n"  +
                        "門前清即叫糊時，手上所有的牌都是自己摸到的，不可有上、碰、明槓之情形發生。" +
                        "立直  在叫糊時（必須門前清）拿出1000點籌碼放在列牌區前，表示「立直」，其後不能改變牌眼、上（吃）牌或碰牌（但在不改變叫糊牌型，意味着在四張牌全部不在順子中的前提下可以做暗槓）。立直食糊時，上方表懸賞牌開出多少張，下方就開多少張裏懸賞（槓裏懸賞同）。\n"  +
                        "排列捨牌  每家的捨牌，按先後順序，從左往右依次擺放在自己門前。正式比賽中一般每一排擺六張，即第七張起擺第二排，第十三張起擺第三排。自己叫糊時，如果所聽的牌之中有任何一張是自己的捨牌，即為「捨牌振聽」，除非沒有立直並換聽成自己未捨過的牌，否則則不能食和別家的捨牌（即使別家打的牌可能並不是自己捨的那張），只能自摸糊牌。\n" +
                        "懸賞牌可分為赤懸賞牌、表懸賞牌、裏懸賞牌、槓懸賞牌、槓裏懸賞牌共5種，其中除赤懸賞牌外其餘4種皆須透過懸賞指示牌獲得，但是懸賞牌只能在成功食糊並且在有番數的情況下計算。赤懸賞牌又稱「紅牌」，指牌上文字、圖樣皆為紅色且有些會有盲人點，牌局不一定會放入，其放入數量在3~6張之間，但大都放3-4張紅牌，主要是數牌5來做紅牌。\n\n"
                );
                playintro.setText(ssb);
                playintro2.setVisibility(View.INVISIBLE);
                playintro3.setVisibility(View.VISIBLE);
                playintro4.setVisibility(View.INVISIBLE);
                playintro5.setVisibility(View.INVISIBLE);


            }
        });

        hkbu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String abc = getResources().getString(R.string.linkhk);

                SpannableStringBuilder ssb = new SpannableStringBuilder();

                ssb.append("起源及介紹\n" +
                        "現代麻將由老章麻將演化而成。經演化後有所謂「清章」的打法。這是香港和廣東麻將的基礎。 \n\n" +
                        "麻雀介紹\n" +
                        "一副麻將牌共有144隻，索/萬/筒子:（一至九各四張），共108張。風牌 :（東南西北）。三元牌:（中發白）每種各四隻，共28張。花牌 :（春夏秋冬、梅蘭菊竹） 共8張。\n\n" +
                        "麻雀玩法/規則說明\n" +
                        "需四人，分別坐在正方形桌子桌子的四側。每人的左方稱為「上家」，右方稱為「下家」。 遊戲開始時每人手中有13張牌。\n " +
                        "經由(上)、(碰)、(槓)、(摸牌)等過程，最快集齊四組「面子」（可以爲「順子」、「刻子」或「槓子」），加一對「眼」，共『13』張牌，即可食糊。\n" +
                        "順子  由三張數字連續的萬子、索子、筒子所組成。\n " +
                        "刻子  由三張同樣的牌組成的牌型。\n" +
                        "眼  由兩張同樣的牌組成的對子。\n" +
                        "開槓  透過開槓則可以將四隻相同的牌當成一組刻子。槓分為明槓及暗槓兩種，明槓為自己手中有三張相同的牌，而其他人打出第四張相同的牌，暗槓為自己手中有四張相同的牌。\n" +
                        "上  從你手內有兩張牌可與上家打出的牌組成順子(注意:你只能吃上家的牌，不能吃對家或下家的牌。)\n" +
                        "碰  從別人的牌湊成刻子為碰牌。 \n" +
                        "計分\n" +
                        "一番: 無花，正花，門前清，平糊，自摸，番牌，台牌  二番: 一台花 ，三番: 花胡，對對糊，混一色  五番: 小三元  七番: 清一色   八番: 大三元，槓上槓自摸，大花胡  十三番: 九子連環，字一色，清么九，十三么\n"
                );
                playintro.setText(ssb);
                playintro2.setVisibility(View.VISIBLE);
                playintro3.setVisibility(View.INVISIBLE);
                playintro4.setVisibility(View.INVISIBLE);
                playintro5.setVisibility(View.INVISIBLE);
            }
        });

        twbu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                SpannableStringBuilder ssb = new SpannableStringBuilder();

                ssb.append("起源及介紹\n" +
                        "臺灣麻將是臺灣常見的麻將玩法，以台數計算。因取牌16張，亦常稱為臺灣16張麻將。在20世紀初期由福建省16張麻將轉化而來，經過時間演變，臺灣麻將已經自成一格。 \n\n"+
                        "麻雀玩法/規則說明\n" +
                        "十六張牌：開局時閒家取16張牌，莊家則取17張牌。\n" +
                        "吃牌的擺放：兩張中間。例如三四萬吃五萬，把五萬放在三萬和四萬中間。（讓其他人知道吃的是哪張。）\n" +
                        "堆牌 洗牌後，四個人將一百四十四張牌，分別堆成二（高）乘十八（底）的「牌牆」四座。PS.若不玩花牌，底則是十七。\n" +
                        "起莊(打莊)  堆牌完成後，由坐在東風位的玩家丟三顆骰子，決定誰是第一個莊家。並且由莊家開始，從自家面前的十八幢牌的最右邊開始數（順時針），一次取兩幢（四張）牌，四家輪流取四次完成配牌。此時每人手上各有十六張牌。例：若骰子擲出11點，則由西風位玩家當莊，並且由莊家開始，從面前取走第十二、十三幢牌（留下右邊十一幢）。\n" +
                        "補花  開門後 , 莊家手裡共有十七張牌，檢查牌中是否有「花牌」，若有則要一次全部出示於「手牌前方」，再從嶺上牌取走相對應的數量讓自己手牌繼續保持十七張牌 , 稱做「補花」。莊家補牌結束後，示意閒家可以依序補花。若補牌補到「花牌」，則要「後補」，程序跟補花相同。\n" +
                        "流局  若牌局進行到剩下「八幢牌」（十六張牌）留在牌牆上還沒有人胡牌，則此局結束, 俗稱「鐵八墩」或「黃一莊」。\n" +
                        "大相公：玩家非胡牌狀況下牌數多於標準牌數（16張），即為大相公。大相公後即無法胡牌亦無法鳴牌。（吃、碰、槓）\n" +
                        "小相公：玩家牌數少於標準牌數，即為小相公。小相公亦無法胡牌，然仍可鳴牌。\n"
                );

                playintro.setText(ssb);
                playintro2.setVisibility(View.INVISIBLE);
                playintro3.setVisibility(View.INVISIBLE);
                playintro4.setVisibility(View.VISIBLE);
                playintro5.setVisibility(View.INVISIBLE);
            }
        });


    }
}