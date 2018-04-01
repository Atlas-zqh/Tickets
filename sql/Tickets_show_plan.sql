CREATE TABLE Tickets.show_plan
(
    id bigint(20) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    description longtext,
    endTime datetime,
    notice longtext,
    showPlanStatus varchar(255),
    showPlanType varchar(255),
    startTime datetime,
    venue_id bigint(20),
    posterUrl varchar(255),
    showName varchar(255),
    CONSTRAINT FK48e4phi7l4yhim6oyk2jwkrvi FOREIGN KEY (venue_id) REFERENCES venue
);
CREATE INDEX FK48e4phi7l4yhim6oyk2jwkrvi ON Tickets.show_plan (venue_id);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (12, '一个无房子、无工作、无车子的“三无”青年忽然得到巨额财产，又阴差阳错地被误认为银行抢劫犯而被警方通缉，知情者要么死去，要么是他的对手，他该如何实现自我救赎？', '2018-03-08 21:20:00', '购买须知：电子票：该项目为电子票，请至少提前一天购买。用户下单后24小时内，票牛会发送取票短信，请按短信提示内容取票。 
儿童入场提示：1.2米以下儿童谢绝入场（儿童项目除外），1.2米以上儿童需持票入场。', 'FINISHED', 'OPERA', '2018-03-08 19:30:00', 7, '/img/3d0439374b02129.jpg', '开心麻花经典舞台剧《乌龙山伯爵》', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (13, '孤女简爱从小被寄养在舅妈家和罗沃德寄宿学校，完成学业后受聘于桑费尔德庄园，和主人罗彻斯特渐生情愫。在婚礼上，简爱发现罗彻斯特已有妻子，伤心离开，被牧师圣约翰所救。简·爱拒绝了圣约翰的求婚，重返桑费尔德庄园。而庄园已是一片废墟，罗彻斯特为救患有精神疾病的妻子被烧瞎双眼。简·爱和罗彻斯特终于成婚。


《简爱》是英国书屋剧院继《呼啸山庄》后再次改编勃朗特姐妹的作品。编剧劳拉·特纳说，虽然勃朗特姐妹的年龄和阅历相仿，但作品却截然不同。相对于《呼啸山庄》的激情、复仇和暴力，《简爱》的爱情则贯穿了年轻女性的成长。简爱的激情、勇气和独立人格使其爱情故事流传百年而芬芳依然。


书屋剧院是英国极负盛名的巡演剧团，创始人是英国著名戏剧导演和音乐制作人理查·梅恩，在英伦三岛和爱尔兰单一演出季观众人数逾10万。其英伦女性经典系列包括勃朗特姐妹和简·奥斯汀的作品，在英国国内广受追捧，被誉为夏日最纯粹的英式享受。其中《呼啸山庄》和《傲慢与偏见》曾来华巡演，在剧中扮演希斯克里夫和达西先生的马修·克里姆斯此次在《简爱》中饰演罗彻斯特。', '2018-05-16 21:00:00', '1.2米以下儿童谢绝入场（儿童项目除外），1.2米以上儿童需持票入场。 原版英文演出 配同步中文字幕 纯正英伦风范  永恒浪漫经典', 'ABUNDANCE', 'OPERA', '2018-05-16 18:30:00', 9, '/img/994b472acbe45a2.jpg', '英国书屋剧院原版经典话剧《简爱》', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (14, '《一个人的莎士比亚》取材于美国演员约瑟夫·格雷夫斯幼年时在伦敦切尔西男童寄宿学校的一段经历和他丰富的莎士比亚戏剧表导演经验，以妙趣横生的手法编织出一个发生在懵懂无知的男孩儿与苛刻霸道的莎士比亚老师之间令人捧腹而又感人至深的故事：


年仅六岁约瑟夫入读一所英国男童寄宿学校，不幸遇到了才华横溢却嗜酒如命的克莱夫·T·瑞维尔校长对新生开展的军训式莎士比亚教育。在迷茫、羞愧与好奇中，小约瑟夫逐渐走进了瑞维尔校长的莎士比亚世界。从对校长的不解和怨恨，到理解与感激，小男孩最终爱上了莎士比亚和那个充满激情的世界，并将其作为自己终身的事业。

《一个人的莎士比亚》自2006年北京人艺剧场第一次演出至今，12年间已在北京、上海、香港、澳门、台北等国内数十个城市，巡回演出200余场。', '2018-04-10 16:00:00', '儿童入场提示：1.2米以下儿童谢绝入场（儿童项目除外），1.2米以上儿童需持票入场。', 'ABUNDANCE', 'OPERA', '2018-04-10 13:25:00', 9, '/img/2c5a2c932c0a9dd.jpg', '一个人的莎士比亚', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (15, '一个女人40年的情与爱，被一枝细腻而绚烂的笔写得哀婉动人，跌宕起伏。20世纪40年代，还是中学生的王琦瑶被选为“上海小姐”，从此开始命运多舛的一生，做了某大员的“金丝雀”，从少女变成了真正的女人。随后大员遇难，上海解放，王琦瑶成了普通百姓，顿似漂萍。表面的日子平淡似水，内心的情感潮水却从未平息，与几个男人的复杂关系，阴差阳错，幽怨百结，想来都是命里注定。', '2018-04-25 21:30:00', '无', 'ABUNDANCE', 'OPERA', '2018-04-25 19:30:00', 9, '/img/5cf76dafd922085.jpg', '话剧《长恨歌》', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (16, '富有诱惑力的叶莲娜引起了万尼亚和乡村医生阿斯特诺夫的关注，这也让一直深爱着阿斯特诺夫的教授女儿索尼娅黯然神伤。万尼亚最终发现他一直崇拜的姐夫只是个极度自私且华而不实的庸才。陷入极度失望的万尼亚舅舅最终带着无比的愤怒向姐夫拔出了手枪……
', '2018-06-06 21:30:00', '无', 'ABUNDANCE', 'OPERA', '2018-06-06 19:30:00', 9, '/img/0e30dece60b3b25.jpg', '契科夫经典话剧《万尼亚舅舅》', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (17, '她是一个在读女博士，他是一个自由音乐人。他们受过高等教育，以社会传播的各种正向准则，不断向彼此确认这样的自己是否算是“好人”。一场在宜家开启的关于“孩子”的谈话炸出了这对年轻情侣潜藏的焦虑。

生个孩子是否只是顺从了人类七千五百代人世代相传的生存机制？这个星球已经承载着70亿人，资源日益匮瘠，生一个孩子是否算是雪上加霜？学业结束后的事业是否真的能有美好的前景？是否只有稳定的职业才能养育好一个孩子？女人生育的痛苦男人无法感同身受，一个孩子的加入是否会给日后的生活带来种种限制？
', '2018-04-26 21:30:00', '无', 'ABUNDANCE', 'OPERA', '2018-04-26 19:30:00', 9, '/img/57dfb1314daa87c.jpg', '《呼吸》2017壹戏剧大赏华语地区最佳小剧场戏剧奖', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (18, '他们曾是爱人，曾是父母。一场不幸的车祸后，他们先是失去了共同的儿子，然后失去了自我，最后失去了彼此。

男人在1999年12月31日晚上19点10分，在千禧年之夜离开了家。他的手无力举起欢庆跨年的酒杯，也再也无力拥抱重创后在精神上无法自控的妻子。他关上了身后的大门，再也没有回来。刚刚失去了独生子的女人继而又失去了生命中最亲近的另一个人。

十年过去了，两个人在埋葬着孩子的墓地再次相见。过往依旧未得平静，伤口依旧未能痊愈。

他，有了新的生活、新的爱，有着对前方的展望。她，不能也不愿让自己的不幸翻篇。她的生活在那一天停止了，停在她儿子死亡的那一天。

时间是治愈伤痛的灵药吗？再次相遇的两人，如何面对彼此，如何面对生命中抹不去的伤痕？

他们曾是爱人，曾是父母。一场不幸的车祸后，他们先是失去了共同的儿子，然后失去了自我，最后失去了彼此。

这是一个关于关系的故事，这是两个迷失灵魂的相遇，关于婚姻、关于爱、关于男人和女人。', '2018-07-01 21:30:00', '无', 'ABUNDANCE', 'OPERA', '2018-07-01 19:30:00', 9, '/img/eb9a42d49daee16.jpg', '《毒》2017 华语戏剧盛典最佳小剧场戏剧', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (19, '任何音乐人最 大的恐惧之一就音乐上来说-是写歌灵感枯竭，但 James Blunt 
似乎永远用不着担心这一点。我们可以绝无夸张地说，他的一生足够写出十几张专辑。我知道所有的创作型歌手都会这么说，但他绝对是一个不一样的创作型歌手，James 
Blunt 有他独特的嗓音、创作细腻而有画面性，像是可以走进他所架构出的时空里，十分能触动人心的底层。2018年 James Blunt 
带着全新自我突破专辑《The Afterlove》回归歌坛，歌迷可能要大大感谢功劳不小的 Ed Sheeran 。本次新曲〈Make Me Better〉由 
Ed Sheeran 担纲制作/写曲/合音，是两人首度合作的歌曲，灵感来自James Blunt 妻子和9个月大的儿子。', '2018-05-25 22:30:00', '购买须知：限购：每单限购6张 儿童入场提示：1.2米以上凭票入场，1.2米以下谢绝入场 禁止携带物品：食品、饮料、相机、充电宝、打火机等。', 'ABUNDANCE', 'CONCERT', '2018-05-25 20:00:00', 8, '/img/290e64f790ff2ad.jpg', '2018年詹姆斯布朗特真情挚爱上海演唱会', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (20, '关于BRUNO MARS
多个格莱美奖得主、获得20次格莱美提名的超级巨星Bruno Mars在全球卖出了超过1.7亿张单曲和2600万张专辑，畅销能力无与伦比！ 
这位广受好评的歌手、词曲作者、制作人、曾在全球范围内以超热单曲《Uptown 
Funk》占据了各大音乐榜单的榜首长达十几周之久。这支单曲在获得“RIAA钻石认证”的同时也顺利入选近十年热门公告牌“100强单曲”，是该榜单设立57年以来， 
第10支霸占Billboard榜首长达14周的超级冠单。', '2018-07-11 22:00:00', '儿童入场提示：儿童一律凭票入场 禁止携带物品：食品、饮料、相机、充电宝、打火机等', 'ABUNDANCE', 'CONCERT', '2018-07-11 19:30:00', 8, '/img/6e9f4a6b64c4065.jpg', 'Bruno Mars 布鲁诺·马尔斯 24K魔法世界巡演上海站', false);
INSERT INTO Tickets.show_plan (id, description, endTime, notice, showPlanStatus, showPlanType, startTime, venue_id, posterUrl, showName, settled) VALUES (21, '罗大佑曾经离开，罗大佑也一直都在。
他把音乐放在前面，自己躲在背后，不停地走。
2017年，罗大佑还在唱着。
当他再次单枪匹马站上个人巡演的舞台，带来的不只是回忆，还有憧憬。

他也将带我们在2018年的演唱会舞台上，
重新走一次三十多年音乐路的起伏跌宕，
用一首一首脍炙人口的歌曲，再写一次永恒的故事。

在喧嚣纷扰的年代，至少我们还拥有一些刻在生命里的歌。
就让经典重现，让传奇流转。', '2018-05-11 22:40:00', '演出时长：演出时长不低于2小时，曲目不少于20首歌 儿童入场提示：儿童一律凭票入场 禁止携带物品：食品、饮料、相机、充电宝、打火机等', 'ABUNDANCE', 'CONCERT', '2018-05-11 19:40:00', 8, '/img/c46448f43e2d0b6.jpg', '2018罗大佑“当年离家的年轻人”巡回演唱会上海站', false);