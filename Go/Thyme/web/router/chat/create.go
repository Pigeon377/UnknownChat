package chat

import (
	"Thyme/web/extension"
	"github.com/gin-gonic/gin"
	"strconv"
)

func CreateRoom(ctx *gin.Context)  {
	uuid := extension.InterceptWithToken(ctx)
	if uuid==-1 {
		return
	}
	roomName := ctx.PostForm("room_name")
	userStringList := ctx.PostFormArray("user_list")
	if roomName == "" || userStringList == nil{
		ctx.JSON(200,gin.H{
			"status":0,
			"message":"InvalidArgument",
			"data":gin.H{},
		})
		return
	}

	var userList = make([]int64 ,len(userStringList))
	for i,v := range userStringList {
		tmp, err := strconv.Atoi(v)
		if err != nil {
			ctx.JSON(200,gin.H{
				"status":0,
				"message":"InvalidArgument",
				"data":gin.H{},
			})
			return
		}
		userList[i] = int64(tmp)
	}
	res := extension.SendCreateRoomMessageWithGrpc(roomName,userList)
	if res.Status {
		ctx.JSON(200, gin.H{
			"status":  1,
			"message": "Succeed",
			"data": gin.H{
				"user_list":     res.UserList,
				"websocket_url": res.RoomWebsocketURL,
				"room_name":     res.RoomName,
			},
		})
	}else{
		ctx.JSON(200, gin.H{
			"status":  0,
			"message": "FailedCreate",
			"data": gin.H{},
		})
	}

}