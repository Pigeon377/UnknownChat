package chat

import (
	"Thyme/web/extension"
	"github.com/gin-gonic/gin"
	"strconv"
)

func SendMessage(ctx *gin.Context) {
	tokenString := ctx.GetHeader("token")
	RoomIDString := ctx.PostForm("room")
	RoomID, err := strconv.Atoi(RoomIDString)
	if err != nil {
		ctx.JSON(200,gin.H{
			"status":0,
			"message":"InvalidArgument",
			"data":gin.H{},
		})
		return
	}
	message := ctx.PostForm("message")
	uuid,judge := extension.ParseToken(tokenString)
	if  !judge {
		ctx.JSON(200,gin.H{
			"status":0,
			"message":"TokenLoseEffectiveness",
			"data":gin.H{},
		})
	}else{
		extension.SendGrpcMessage(uuid, int64(RoomID),message)
		ctx.JSON(200,gin.H{
			"status":1,
			"message":"Success",
			"data":gin.H{},
		})
	}
}
