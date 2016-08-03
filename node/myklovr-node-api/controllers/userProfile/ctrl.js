var CONSTANTS = require('../../helpers/constants.js');
var service = require('../../services/requestRest.js');
var uuid = require('node-uuid');


var conectionsPool = [];


var model = {
    OperationStatus:'',
    Message: {
        Text:'',
        Level:'',
    },
    OperationData: ''
}

var initSectionKindData =[
    {id:'KND_Accademic ',value:false},
    {id:'KND_Personal',value:false},
    {id:'KND_Professional',value:false},
    {id:'KND_EnhancedStudentEnrollment',value:false},
    {id:'KND_StudentRetention',value:false},
    {id:'KND_IncrestStudentSchoolRank',value:false},
    {id:'KND_InterpretationML',value:false},
    {id:'KND_AdvertisingPromotion',value:false},
    {id:'KND_Sell',value:false},
    {id:'KND_AdvertisingPromotion',value:false},
    {id:'KND_HR',value:false},
    {id:'KND_JobPosting',value:false},
    {id:'KND_OnBoardinSol',value:false},
    {id:'KND_MarketResearch',value:false},
    {id:'KND_',value:false}];

var initSectionBasicInfo = [
    {id:'BUI_FirstName', value:''},
    {id:'BUI_LastName', value:''},
    {id:'BUI_JobTitle', value:''},
    {id:'BUI_MobileNumber', value:''},
    {id:'BUI_CompanyName', value:''},
    {id:'BUI_IndustryType', value:''},
    {id:'BUI_Country', value:''},
    {id:'BUI_State', value:''},
    {id:'BUI_City', value:''},
    {id:'BUI_ZipCode', value:''},
    {id:'BUI_DateOfBirth', value:''},
    {id:'BUI_Nickname', value:''},
    {id:'BUI_Gender', value:''},
    {id:'BUI_Subjects', value:''},
    {id:'BUI_PhoneNumber', value:''},
    {id:'BUI_SchoolName', value:''},
    {id:'BUI_SchoolType', value:''},
    {id:'BUI_Grade', value:''},
]

var initSectionAccademicGrowth=[
    {id:'UP_AG_GeneralClassHelp',value:false},
    {id:'UP_AG_FindingTutorExpert',value:false},
    {id:'UP_AG_TakingStandardizedTest',value:false},
    {id:'UP_AG_CourseSelection',value:false},
    {id:'UP_AG_ResearchingRightCollage',value:false},
    {id:'UP_AG_ReviewChildCurriculumPerformance',value:false},
    {id:'UP_AG_ResourcesDiscussChildFuturePlans',value:false},
    {id:'UP_AG_CollageApplicationProcess',value:false},
    {id:'UP_AG_SelectingMajorCareerPath',value:false},
    {id:'UP_AG_TransitoringIntoCollageLife',value:false},
    {id:'UP_AG_ResearchingTradeSchools',value:false},
    {id:'UP_AG_CourseSelectionGraduateProgram',value:false},
    {id:'UP_AG_GraduateSchoolApplicationProcess',value:false}];
var initSectionProfessionalGrowth=[
    {id:'UP_PROG_ExploringCareerPaths',value:false},
    {id:'UP_PROG_SettingCareerGoals',value:false},
    {id:'UP_PROG_DiscoveringPassions',value:false},
    {id:'UP_PROG_DiscoverintStrenghts',value:false},
    {id:'UP_PROG_InterviewSkills',value:false},
    {id:'UP_PROG_JobApplication',value:false},
    {id:'UP_PROG_NetworkingEvents',value:false},
    {id:'UP_PROG_FinancialAid',value:false},
    {id:'UP_PROG_JobSearch',value:false},
    {id:'UP_PROG_TransitoringProfessionalLife',value:false},
    {id:'UP_PROG_ContinuedLearningOptionsCareer',value:false},
    {id:'UP_PROG_SettingCareerGoals',value:false}]
var initSectionPersonalGrowth=[
    {id:'UP_PERG_ImprovingHealthFitness',value:false},
    {id:'UP_PERG_FindingFriendsSimilarInterest',value:false},
    {id:'UP_PERG_ManagingRelationshipsFriendships',value:false},
    {id:'UP_PERG_ExploringNewHobbies',value:false},
    {id:'UP_PERG_SelectingVolunteerExtracurricularActivities',value:false},
    {id:'UP_PERG_HandingStressAnxiety',value:false},
    {id:'UP_PERG_CalenderRelevantEvents',value:false},
    {id:'UP_PERG_TimeManagement',value:false}];
var initSectionRecruitingGrowth=[
    {id:'UP_REC_SearchQualityCandidates',value:false},
    {id:'UP_REC_BetterTrainingNewHires',value:false},
    {id:'UP_REC_PersonalizedAssistenceCandidates',value:false},
    {id:'UP_REC_ContinueEducationEmployees',value:false},
    {id:'UP_REC_ImproveRetentionRate',value:false},
    {id:'UP_REC_AssessingStudentQuality',value:false},
    {id:'UP_REC_AccessDesiredStdentPool',value:false},
    {id:'UP_REC_OutreachStudentBeforeApply',value:false},
    {id:'UP_REC_ImproveEnrollmentRate',value:false}];
var initSectionMarketResearch=[
    {id:'UP_MKT_UnderstandLaborMarketTrends',value:false},
    {id:'UP_MKT_AccessDataInsightsInterpretation',value:false},
    {id:'UP_MKT_UnderstandBestBusinessPractices',value:false},
    {id:'UP_MKT_IdentifySchoolsTarget',value:false},
    {id:'UP_MKT_IdentifyStudentPopulationsTarget',value:false}];
var initSectionPromoting=[
    {id:'UP_PROMO_PostJobsInterships',value:false},
    {id:'UP_PROMO_Advertise',value:false},
    {id:'UP_PROMO_ShareResourceInfo',value:false}];
var initSectionResearchDevelopment=[
    {id:'UP_RD_BetterUnderstandSchollCompetitors',value:false},
    {id:'UP_RD_ImproveMarketingStrategy',value:false},
    {id:'UP_RD_ImproveCurriculum',value:false}];
var initSectionCareerServices=[
    {id:'UP_CS_GuidanceCounselingSupport',value:false},
    {id:'UP_CS_ImproveSocialNetworksStudents',value:false},
    {id:'UP_CS_ConnectStudentsJobInterships',value:false},
    {id:'UP_CS_PrepareStudentsCarrers',value:false},
    {id:'UP_CS_PartnerCompanies',value:false},
    {id:'UP_CS_ImproveStudentJobPlacement',value:false}];


var kindSelected;
var sectionKindSelected;
var sectionBasicInfoSelected;
var sectionAccademicGrowthSelected;
var sectionProfessionalGrowthSelected;
var sectionPersonalGrowthSelected;
var sectionRecruitingGrowthSelected;
var sectionMarketResearchSelected;
var sectionPromotingSelected;
var sectionResearchDevelopmentSelected;
var sectionCareerServicesSelected;

var invalidArgumentResponse = {
    OperationStatus:3,
    Message: {
        Text:"Invalid arguments",
        Level:'ERROR',
    },
    OperationData: ''
}


exports.getUserSectionKind = function(req, res) {
    console.log('GET getSectionUserKind');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var sessionToken = req.cookies.sessionToken;
    if (sessionToken === undefined){
        req.status(200).jsonp(invalidArgumentResponse);
    }else{
        var connUUID = uuid.v1();
        conectionsPool[connUUID] = {request: req,response:res};
        var section = req.body.SectionKind;
        section.kind = req.body.Kind;
        var requestModel = {
            sectionName:'SectionKind',
            values: '',
            sessionToken:sessionToken,
            nextPage:''
        }
        console.log(JSON.stringify(requestModel));
        service.requestPost(requestModel,CONSTANTS.SERVICES.USER_PROFILE.GET_SECTION,response,connUUID);
    }
};

exports.setUserSectionKind = function(req, res) {
    console.log('SET getSectionUserKind');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var sessionToken = req.cookies.sessionToken;
    if ((req.body.SectionKind === undefined)||(req.body.Kind === undefined) || (sessionToken === undefined)){
        req.status(200).jsonp(invalidArgumentResponse);
    }else{
        var connUUID = uuid.v1();
        conectionsPool[connUUID] = {request: req,response:res};
        var section = req.body.SectionKind;
        section.kind = req.body.Kind;
        var requestModel = {
            sectionName:'SectionKind',
            values: section,
            sessionToken:sessionToken,
            nextPage:'WIZARD_2'
        }
        console.log(JSON.stringify(requestModel));
        service.requestPost(requestModel,CONSTANTS.SERVICES.USER_PROFILE.SET_SECTION,response,connUUID);
    }
};

exports.getUserSectionBasicInfo = function(req, res) {
    console.log('SET setUserSectionBasicInfo');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var sessionToken = req.cookies.sessionToken;
    if (sessionToken === undefined){
        req.status(200).jsonp(invalidArgumentResponse);
    }else{
        var connUUID = uuid.v1();
        conectionsPool[connUUID] = {request: req,response:res};
        var requestModel = {
            sectionName:'SectionBasicInfo',
            values: '',
            sessionToken:sessionToken,
            nextPage:''
        }
        console.log(JSON.stringify(requestModel));
        service.requestPost(requestModel,CONSTANTS.SERVICES.USER_PROFILE.GET_SECTION,response,connUUID);
    }
};

exports.setUserSectionBasicInfo = function(req, res) {
    console.log('SET setUserSectionBasicInfo');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var sessionToken = req.cookies.sessionToken;
    if ((req.body.SectionBasicInfo === undefined)||(req.body.Kind === undefined) || (sessionToken === undefined)){
        req.status(200).jsonp(invalidArgumentResponse);
    }else{
        var connUUID = uuid.v1();
        conectionsPool[connUUID] = {request: req,response:res};
        var section = req.body.SectionBasicInfo;
        var requestModel = {
            sectionName:'SectionBasicInfo',
            values: section,
            sessionToken:sessionToken,
            nextPage:'WIZARD_3'
        }
        console.log(JSON.stringify(requestModel));
        service.requestPost(requestModel,CONSTANTS.SERVICES.USER_PROFILE.SET_SECTION,response,connUUID);
    }
};

exports.getUserSectionPurpose = function(req, res) {
    console.log('SET setUserSectionBasicInfo');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var sessionToken = req.cookies.sessionToken;
    if (sessionToken === undefined){
        req.status(200).jsonp(invalidArgumentResponse);
    }else{
        var connUUID = uuid.v1();
        conectionsPool[connUUID] = {request: req,response:res};
        var section = req.body.SectionBasicInfo;
        var requestModel = {
            sectionName:'SectionBasicInfo',
            values: '',
            sessionToken:sessionToken,
            nextPage:''
        }
        console.log(JSON.stringify(requestModel));
        service.requestPost(requestModel,CONSTANTS.SERVICES.USER_PROFILE.GET_SECTION,response,connUUID);
    }
};

exports.setUserSectionPurpose = function(req, res) {

    console.log('SET setUserSectionBasicInfo');
    console.log("Parameteres: " + JSON.stringify(req.body));
    var sessionToken = req.cookies.sessionToken;
    if (((req.body.SectionAccademicGrowth === undefined)    &&
         (req.body.SectionProfessionalGrowth === undefined) &&
         (req.body.SectionPersonalGrowth === undefined)     &&
         (req.body.SectionRecruitingGrowth === undefined)   &&
         (req.body.SectionMarketResearch === undefined)     &&
         (req.body.SectionPromoting === undefined)          &&
         (req.body.SectionResearchDevelopment === undefined)&&
         (req.body.SectionCareerServices === undefined))    || (sessionToken === undefined)){
        req.status(200).jsonp(invalidArgumentResponse);
    }else{
        var connUUID = uuid.v1();
        conectionsPool[connUUID] = {request: req,response:res, callCounter:0};
        if (req.body.SectionAccademicGrowth != undefined){
            conectionsPool[connUUID].callCounter = conectionsPool[connUUID].callCounter+1;
            var section = req.body.SectionBasicInfo;
            var requestModel = {
                sectionName:'SectionAccademicGrowth',
                values: section,
                sessionToken:sessionToken,
                nextPage:'HOME'
            }
            service.requestPost(requestModel,CONSTANTS.SERVICES.USER_PROFILE.SET_SECTION,verifySectionPurposeEnd,connUUID);
        }
    }
};

function verifySectionPurposeEnd(statusCode,model, connUUID){
    if (conectionsPool[connUUID].callCounter > 0 ){

    }else{
        if (statusCode != 200){

        }
    }
}


function response(statusCode,model, connUUID){
    var connection = conectionsPool[connUUID];
    delete conectionsPool[connUUID];
    var res = connection.response;
    res.status(statusCode).jsonp(model);
}