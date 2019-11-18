function createUUID() {
  
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";
  
    var uuid = s.join("");
    return uuid;
}



$(document).ready(function() {
  
    //$('body').hide().fadeIn(5000);







   $('#submit').click(function() {
    var failure = function(err) {
             alert("Unable to retrive data "+err);
   };
  
    //Get the user-defined values that represent claim data to persist in the Adobe CQ JCR
    var username= $('#Username').val() ; 
    var password= $('#Password').val();
    
    var claimId = createUUID(); 
  
  
    //Use JQuery AJAX request to post data to a Sling Servlet
    $.ajax({
         type: 'POST',    
         url:'/bin/htlSearchServlet',
         //data:'id='+ claimId+'&firstName='+ myFirst+'&lastName='+ myLast+'&address='+ address+'&cat='+ cat+'&state='+ state+'&details='+ details+'&date='+ date+'&city='+ city,
         data:'id='+ claimId+'&username='+ username+'&password='+ Password,
         success: function(msg){
 
           var json = jQuery.parseJSON(msg); 
            var msgId=   json.id;
            var lastName = json.lastname;
            var firstName = json.firstname;
              
            $('#ClaimNum').val(msgId); 
           // $('#json').val("Filed by " + firstName + " " + lastName);   
         }
     });
  });

});
