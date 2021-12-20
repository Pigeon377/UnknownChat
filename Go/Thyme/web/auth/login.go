package auth

import (
	"Thyme/web/extension"
	"Thyme/web/model"
	"github.com/gin-gonic/gin"
)

func Login(ctx *gin.Context){
	var user model.User
	err := ctx.Bind(&user)
	if err != nil {
		ctx.JSON(400,"Illegal ContentType")
		return
	}
	if user.Mailbox == "" || user.Password == "" {
		ctx.JSON(200,gin.H{
			"status":0,
			"message":"InvalidArgument",
			"data":gin.H{},
		})
		return
	}
	var tempUser model.User
	extension.DB.First(&tempUser,"mailbox = ?",user.Mailbox)
	if tempUser.Password == "" {  // 不存在该用户
		ctx.JSON(200,gin.H{
			"status":0,
			"message":"MailboxUnMatchPassword",
			"data":gin.H{},
		})
		return
	}
	if extension.CheckPasswordHash(user.Password,tempUser.Password){
		ctx.JSON(200,gin.H{
			"status":1,
			"message":"Succeed",
			"data":gin.H{
				"token":114514,
			},
		})
	}else{
		ctx.JSON(200,gin.H{
			"status":0,
			"message":"MailboxUnMatchPassword",
			"data":gin.H{},
		})
	}
	return
}
