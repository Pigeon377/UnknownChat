package auth

import (
	"Thyme/web/extension"
	"Thyme/web/model"
	"fmt"
	"github.com/gin-gonic/gin"
)

func Register(ctx *gin.Context) {
	var user model.User
	err := ctx.Bind(&user)
	if err != nil {
		ctx.JSON(400,"Illegal ContentType")
		return
	}
	if user.Name == "" || user.Password == "" || user.Mailbox == "" {
		ctx.JSON(200,gin.H{
			"status":0,
			"message":"InvalidArgument",
			"data":gin.H{},
		})
		return
	}

	var tempUser model.User
	extension.DB.First(&tempUser,"mailbox = ?",user.Mailbox)
	fmt.Println(tempUser)
	if tempUser.Name !=""||tempUser.Password !=""||tempUser.Mailbox !="" {
		ctx.JSON(200,gin.H{
			"status":0,
			"message":"UserExist",
			"data":gin.H{},
		})
		return
	}

	user.Password = extension.GeneratePasswordHash(user.Password)
	extension.DB.Create(&user)
	ctx.JSON(200, gin.H{
		"status":1,
		"message":"Succeed",
		"data":gin.H{},
	})
	return
}
