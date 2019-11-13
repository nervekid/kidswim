
CREATE TABLE `ach_t_balance` (

`ID` varchar(64) NOT NULL,

`yearMonth` varchar(8) NULL COMMENT '年月',

`enterpriseId` varchar(64) NULL COMMENT '企业id',

`enterpriseName` varchar(64) NULL COMMENT '企业名称',

`enterpriseNum` varchar(64) NULL COMMENT '企业账号',

`balance` decimal NULL COMMENT '余额',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='余额'
;



CREATE TABLE `ach_t_rechargeNotReconciled` (

`ID` varchar(64) NOT NULL,

`yearMonth` varchar(8) NULL COMMENT '年月',

`enterpriseId` varchar(64) NULL COMMENT '企业id',

`enterpriseName` varchar(64) NULL COMMENT '企业名称',

`enterpriseNum` varchar(64) NULL COMMENT '企业账号',

`rechargeTime` datetime NULL COMMENT '充值时间',

`rechargeAmount` decimal NULL COMMENT '充值金额',

`donationAmount` decimal NULL COMMENT '赠送余额',

`checkedTime` datetime NULL COMMENT '对账时间',

`checkedAmount` decimal NULL COMMENT '已对账金额',

`unCheckedAmount` decimal NULL COMMENT '未对账金额',

`rechargeUser` varchar(64) NULL COMMENT '充值人员',

`belonger` varchar(64) NULL COMMENT '归属人',

`enterpriseSource` int(4) NULL COMMENT '企业来源',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='充值未对账数据'
;
-----------------------------------------------


1：增删查改
CREATE TABLE `ach_t_custCorrespondEnterprise` (

`ID` varchar(64) NOT NULL,
`customeCode` varchar(64) NULL COMMENT '客户编码',
`customerName` varchar(64) NULL COMMENT '客户名称',
`oldCustomer_flag` int(4) NULL COMMENT '是否老客户',
`create_by` varchar(64) NULL COMMENT '创建人',
`create_date` datetime NULL COMMENT '创建时间',
`update_by` varchar(64) NULL COMMENT '更新人',
`update_date` datetime NULL COMMENT '更新时间',
`remarks` varchar(255) NULL COMMENT '备注',
`del_flag` varchar(2) NULL COMMENT '删除标志',
PRIMARY KEY (`ID`) 


)

COMMENT='客户对应企业账号'
;
--雁冰

CREATE TABLE `ach_t_custCorrespondEnterpriseEntry` (

`ID` varchar(64) NOT NULL,

`parentId` varchar(64) NULL COMMENT '客户对应企业账号ID ach_t_custCorrespondEnterprise 主键id',

`enterpriseId` varchar(64) NULL COMMENT '企业账号ID ach_t_enterpriseSalesDivision 主键id',

PRIMARY KEY (`ID`) 

)

COMMENT='客户对应企业账号子表'
;
--雁冰

1：增删查改
CREATE TABLE `ach_t_enterpriseSalesDivision` (

`ID` varchar(64) NOT NULL,
`yearMonth` varchar(8) NOT NULL COMMENT '年月',
`enterpriseNum` varchar(64) NULL COMMENT '企业账号',
`enterpriseName` varchar(64) NULL COMMENT '企业名称',
`businessType` int(4) NULL COMMENT '业务类型 business_type	1：短彩信 2：月租 3：享流量 4：码号 5：UMP 6：语音',
`source` int(4) NULL COMMENT '来源 source 1:MOS 2:400 3:其他',
`payType` int(4) NULL COMMENT '付费类型 pay_type 0:预付费 1：后付费  新的：2: 代收代缴 3：客户自付',
`masId` varchar(64) NULL COMMENT 'masId',
`create_by` varchar(64) NULL COMMENT '创建人',
`create_date` datetime NULL COMMENT '创建时间',
`update_by` varchar(64) NULL COMMENT '更新人',
`update_date` datetime NULL COMMENT '更新时间',
`remarks` varchar(255) NULL COMMENT '备注',
`del_flag` varchar(2) NULL COMMENT '删除标志',
PRIMARY KEY (`ID`) 


)
COMMENT='企业销售分成'
;
--雁冰

CREATE TABLE `ach_t_enterpriseSalesDivisionEntry` (

`ID` varchar(64) NOT NULL,
`yearMonth` varchar(8) NOT NULL COMMENT '年月',
`enterpriseId` varchar(64) NULL COMMENT '父表id ach_t_enterpriseSalesDivision主键id',
`userId` varchar(64) NULL COMMENT '销售id SysUserHistory对象',
`occupationRate` decimal NULL COMMENT '提成占比',
`achievementRate` decimal NULL COMMENT '业绩占比',
`mainSales_flag` int(4) NULL COMMENT '是否主导销售',
`create_by` varchar(64) NULL COMMENT '创建人',
`create_date` datetime NULL COMMENT '创建时间',
`update_by` varchar(64) NULL COMMENT '更新人',
`update_date` datetime NULL COMMENT '更新时间',
`remarks` varchar(255) NULL COMMENT '备注',
`del_flag` varchar(2) NULL COMMENT '删除标志',
PRIMARY KEY (`ID`) 

)

COMMENT='企业销售分成子表'
;
--雁冰
-----------------------------------------------------------
1：增删查改

CREATE TABLE `ach_t_coefficientExtraction` (

`ID` varchar(64) NOT NULL,

`userId` varchar(64) NULL COMMENT '用户id SysUserHistory对象',

`commissionRate` decimal NULL COMMENT '提成系数',

`yearMonth` varchar(64) NULL COMMENT '年月',

`completionRate` decimal(2) NULL COMMENT '业绩完成率',

`achievement` decimal NULL COMMENT '当月业绩',

`task` int(4) NULL COMMENT '当月任务',

`status` int(4) NULL COMMENT '状态',

`deptId` varchar(64) NULL COMMENT '部门id SysOfficeHistory对象',

`userType` int(4) NULL COMMENT '用户类型 user_type 1:销售 2：经理 3：总监',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='提成系数'
;
--宇维

1：增删查改
CREATE TABLE `ach_t_accountManagement` (

`ID` varchar(64) NOT NULL,

`enterpriseId` varchar(64) NULL COMMENT '企业id ach_t_enterpriseSalesDivision 主键id',

`receivableYearMonth` varchar(8) NULL COMMENT '应收年月',

`paymentTime` datetime NULL COMMENT '到款日期',

`status` int(4) NULL COMMENT '状态 account_status 0:待确认 1:已确认',

`amount` decimal NULL COMMENT '金额',

`accountType` int NULL COMMENT '到账类型 account_type 0:预付费 1：后付费 2：酬金',

`confirmationTime` datetime NULL COMMENT '确认时间',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='对账管理'
;
--宇维



1：增删查改
CREATE TABLE `ach_t_remunerationStatements` (

`ID` varchar(64) NOT NULL,

`yearMonth` varchar(8) NULL COMMENT '年月',

`channelId` varchar(64) NULL COMMENT '通道ID',

`enterpriseId` varchar(64) NULL COMMENT '企业ID  ach_t_enterpriseSalesDivision 主键id',

`channelName` varchar(64) NULL COMMENT '通道名称',

`remuneration` decimal NULL COMMENT '酬金',

`accountAmount` decimal NULL COMMENT '到账金额',

`accountDate` datetime NULL COMMENT '到账时间',

`difference` decimal NULL COMMENT '应结未结',

`accountType` int NULL COMMENT '到账类型 account_type 0:预付费 1：后付费 2：酬金',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 
)
COMMENT='酬金对账单'
;



1：增删查改
CREATE TABLE `ach_t_productRoportion` (

`ID` varchar(64) NOT NULL,

`businessType` int(4) NULL COMMENT '业务类型 business_type	1：短彩信 2：月租 3：享流量 4：码号 5：UMP 6：语音',

`salesRate` decimal NULL COMMENT '销售人员提成比例',

`managerRate` decimal NULL COMMENT '经理提成比例',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='产品提成比例'
;
--宇维

CREATE TABLE `ach_t_majordomoRate` (

`ID` varchar(64) NOT NULL,

`userId` varchar(64) NULL COMMENT '总监id',

`areaId` varchar(64) NULL COMMENT '区域(组织架构)',

`beginLadder` int(4) NULL COMMENT '开始阶梯',

`endLadder` int(4) NULL COMMENT '结束阶梯',

`businessType` int(4) NULL COMMENT '业务类型 （ 枚举 business_type	1：短彩信 2：月租 3：享流量 4：码号 5：UMP 6：语音）',

`commissionRate`   decimal null  COMMENT '提成比例',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='总监提成比例'
;
--雁冰


1：增删查
CREATE TABLE `ach_t_managerWithCustomer` (

`ID` varchar(64) NOT NULL,

`customerId` varchar(64) NULL COMMENT '客户id ach_t_custCorrespondEnterprise 表id',

`managerId` varchar(64) NULL COMMENT '销售经理id User对象',

`productType` int(4) NULL COMMENT '产品线类型 product_type	1：即信 ',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='销售经理自跟客户'
;


1：查 导
CREATE TABLE `ach_t_customerCooperationTerm` (

`ID` varchar(64) NOT NULL,

`yearMonth` varchar(8) NULL COMMENT '年月',


`enterpriseId` varchar(64) NULL COMMENT '企业id ach_t_enterpriseSalesDivision 主键id',

`payType` int(4) NULL COMMENT '付费类型 pay_type 0:预付费 1：后付费  新的：2: 代收代缴 3：客户自付',

`beginDate` datetime NULL COMMENT '开始合作时间',

`monthCount` int(4) NULL COMMENT '合作月数',

`salesRate` decimal NULL COMMENT '销售提成点数',

`managerRate` decimal NULL COMMENT '经理提成点数',

`manual_flag` int(4) NULL COMMENT '是否手工维护',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='客户合作期限'
;
--雁冰


1：查导

CREATE TABLE `ach_t_custCorrespondSubcust` (

`ID` varchar(64) NOT NULL,

`customerId` varchar(64) NULL COMMENT '客户id ach_t_custCorrespondEnterprise 表id',

`subCustomerId` varchar(64) NULL COMMENT '子客户id ach_t_custCorrespondEnterprise 表id',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='客户对应子客户'
;
--宇维



CREATE TABLE `ach_t_offsetData` (

`ID` varchar(64) NOT NULL,

`yearMonth` varchar(8) NULL COMMENT '年月',

`receivableId` varchar(64) NULL COMMENT '应收id',

`arriveAccountId` varchar(64) NULL COMMENT '到账id',

`accountType` int NULL COMMENT '到账类型 account_type 0:预付费 1：后付费 2：酬金',

`settlementStatus` int(8) NULL COMMENT '结算状态 settlement_status 0:未结算 1:部分结算 2:已结算',

`matchAmount` decimal(8) NULL COMMENT '冲抵金额',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',


PRIMARY KEY (`ID`) 
)
COMMENT='应收到账及冲抵数据'
;



CREATE TABLE `ach_t_organizational` (

`ID` varchar(64) NOT NULL,

`yearMonth` varchar(8) NULL COMMENT '年月',

`salesId` varchar(64) NULL COMMENT '销售ID SysUserHistory',

`managerId` varchar(64) NULL COMMENT '经理id SysUserHistory',

`majordomoId` varchar(64) NULL COMMENT '总监id SysUserHistory',

`deptId` varchar(64) NULL COMMENT '部门Id SysOfficeHistory',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='组织架构'
;



CREATE TABLE `ach_t_receivableManage` (
`ID` varchar(64) NOT NULL,
`yearMonth` varchar(64) NOT NULL COMMENT '年月',
`masId` varchar(64) NULL COMMENT 'masId',
`enterpriseId` varchar(64) NULL COMMENT '企业ID',
`smsType` int(4) NULL COMMENT '短信类型 sms_type 0:短信 1:彩信',
`deductionType` varchar(64) NULL  COMMENT '扣费方式 按提交量,按发送量,成功量',
`billingQuantity` decimal(10,2) NULL COMMENT '计费量',
`customerPrice` decimal(10,2) NULL COMMENT '客户单价',
`theoryincom` decimal(10,2) NULL COMMENT '理论应收',
`giving` decimal(10,2) NULL COMMENT '赠送',
`realincome` decimal(10,2) NULL COMMENT '实际应收',
`settleDiff` decimal(10,2) NULL COMMENT '结算差异',
`cost` decimal(10,2) NULL COMMENT '成本',
`costDiff` decimal(10,2) NULL COMMENT '成本差异',
`complaintRate` decimal(10,2) NULL  COMMENT '投诉率',
`complaintsCost` decimal(10,2) NULL COMMENT '投诉成本',
`allocateSubsidies` decimal(10,2) NULL COMMENT '调配补贴',
`policySubsidies` decimal(10,2) NULL COMMENT '政策补贴',
`rebatesFee` decimal(10,2) NULL COMMENT '返点酬金',
`rewardRemuneration` decimal(10,2) NULL COMMENT '奖励酬金',
`compensationDiff` decimal(10,2) NULL COMMENT '酬金差异',
`otherAdjustment` decimal(10,2) NULL COMMENT '其他调整',
`marketingAgencyFee` decimal(10,2) NULL COMMENT '营销代理费',
`resultsGrossMargin` decimal(10,2) NULL COMMENT '业绩毛利',
`salesCost` decimal(10,2) NULL COMMENT '销售费用',
`tallageFee` decimal(10,2) NULL COMMENT '税费',
`commissionMargin` decimal(10,2) NULL COMMENT '提成毛利',
`EASRemarks` varchar(255) NULL COMMENT 'EAS备注',
`submitStatus` int(4) NULL COMMENT '提交状态（submit_status 0：未确认 1：已确认）',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '提成备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`)
 )

COMMENT='应收管理'
;

----------------------------------------------------------------------------------------

CREATE TABLE `ach_t_commissionDetailBasic` (

`ID` varchar(64) NOT NULL,

`yearMonth` varchar(8) NULL COMMENT '年月',

`batch` varchar(8) NULL COMMENT '发放批次',

`enterpriseId` varchar(64) NULL COMMENT '企业Id',

`actualReceivable` decimal NULL COMMENT '实际应收',

`grossMargin` decimal NULL COMMENT '提成毛利',

`actualCashBack` decimal NULL COMMENT '实际回款',

`realGrossProfit` decimal NULL COMMENT '实发毛利',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='提成明细基础表'
;


CREATE TABLE `ach_t_userCommission` (

`ID` varchar(64) NOT NULL,

`userId` varchar(64) NULL COMMENT '用户id SysUserHistory',

`productRate` decimal NULL COMMENT '产品比例',

`commissionRate` decimal NULL COMMENT '提成系数',

`shouldCommission` decimal NULL COMMENT '应发提成',

`realCommission` decimal NULL COMMENT '实发提成',

`parentId` varchar(64) NULL COMMENT '提成明细基础表id',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='个人提成'
;

CREATE TABLE `ach_t_teamCommission` (

`ID` varchar(64) NOT NULL,

`userId` varchar(64) NULL COMMENT '用户id SysUserHistory',

`productRate` decimal NULL COMMENT '产品比例',

`commissionRate` decimal NULL COMMENT '提成系数',

`shouldCommission` decimal NULL COMMENT '应发提成',

`realCommission` decimal NULL COMMENT '实发提成',

`parentId` varchar(64) NULL COMMENT '提成明细基础表id',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='团队提成'
;

CREATE TABLE `ach_t_majordomoCommission` (

`ID` varchar(64) NOT NULL,

`userId` varchar(64) NULL COMMENT '用户id SysUserHistory',

`productRate` decimal NULL COMMENT '产品比例',

`commissionRate` decimal NULL COMMENT '提成系数',

`shouldCommission` decimal NULL COMMENT '应发提成',

`realCommission` decimal NULL COMMENT '实发提成',

`parentId` varchar(64) NULL COMMENT '提成明细基础表id',

`create_by` varchar(64) NULL COMMENT '创建人',

`create_date` datetime NULL COMMENT '创建时间',

`update_by` varchar(64) NULL COMMENT '更新人',

`update_date` datetime NULL COMMENT '更新时间',

`remarks` varchar(255) NULL COMMENT '备注',

`del_flag` varchar(2) NULL COMMENT '删除标志',

PRIMARY KEY (`ID`) 

)

COMMENT='总监提成'
;


