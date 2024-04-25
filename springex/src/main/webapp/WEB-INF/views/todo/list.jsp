<%--
  Created by IntelliJ IDEA.
  User: DAIN
  Date: 24. 4. 19.
  Time: 오후 3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <title>Hello, world!</title>
    <style>
        .center-float {
            position: absolute;
            left: 50%;
            transform: translateX(-50%);
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <%--        <h1>Header</h1>--%>
        <div class="row">
            <div class="col">
                <nav class="navbar navbar-expand-lg navbar-light bg-light">
                    <div class="container-fluid">
                        <a class="navbar-brand" href="#">Navbar</a>
                        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                                aria-expanded="false" aria-label="Toggle navigation">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="nabarNavAltMarkup">
                            <div class="navbar-nav">
                                <a class="nav-link active" aria-current="page" href="#">home</a>
                                <a class="nav-link active" href="#">Features</a>
                                <a class="nav-link active" href="#">Pricing</a>
                                <a class="nav-link active" href="#">Disabled</a>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
            <div class="row Content">
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Search</h5>
                            <form action="todo/list" method="get">
                                <input type="hidden" name="size" value="${pageRequestDTO.size}">
                                <div class="mb-3">
                                    <input type="checkbox" name="finished" ${pageRequestDTO.finished?"checked":""}> 완료여부
                                </div>
                                <div class="mb-3">
                                    <input type="checkbox" name="types" value="t" ${pageRequestDTO.checkType("t")?"checked":""}>제목
                                    <input type="checkbox" name="types" value="w" ${pageRequestDTO.checkType("w")?"checked":""}>작성자
                                    <input type="text" name="keyword" class="form-control" value='<c:out value="${pageRequestDTO.keyword}"/>'>
                                </div>
                                <div class="input-group mb-3 dueDateDiv">
                                    <input type="date" name="from" value="form-control" value="${pageRequestDTO.from}">
                                    <input type="date" name="to" value="form-control" value="${pageRequestDTO.to}">
                                </div>
                                <div class="input-group mb-3">
                                    <div class="float-end">
                                        <button class="btn btn-primary" type="submit">Search</button>
                                        <button class="btn btn-info clearBtn" type="reset">Clear</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <%--            <h1>Content</h1>--%>
            </div>
        <div class="row content">
            <div class="col">
                <div class="card">
                    <div class="card-header">
                        Featured
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Special title treatment</h5>
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col">Tno</th>
                                <th scope="col">Title</th>
                                <th scope="col">Writer</th>
                                <th scope="col">DueDate</th>
                                <th scope="col">Finished</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%--                            페이징 처리부분 서버에서 전달받은 모델 인스턴스를 불러와서 화면에 이용하기--%>
                            <c:forEach items="${responseDTO.dtoList}" var="dto">
                                <tr>
                                    <th scope="row"><c:out value="${dto.tno}"/></th>
                                    <td><a href="/todo/read?tno=${dto.tno}&${pageRequestDTO.link}" class="text-decoration-none" data-tno="${dto.tno}">
                                        <c:out value="${dto.title}"/></a></td>
                                    <td><c:out value="${dto.writer}"/></td>
                                    <td><c:out value="${dto.dueDate}"/></td>
                                    <td><c:out value="${dto.finished}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <%--                        페이징 부트스트랩의 컴포넌트 요소 넣기--%>
                        <div class="center-float">
                            <ul class="pagination flex=wrap">
                                <%--                            이전 버튼 표시--%>
                                <c:if test="${responseDTO.prev}">
                                    <li class="page-item">
                                        <a class="page-link" data-num="${responseDTO.start - 1}">이전</a>
                                    </li>
                                </c:if>
                                <%--                                현재 페이지 버튼 표시--%>
                                <c:forEach begin="${responseDTO.start}" end="${responseDTO.end}" var="num">
                                    <li class="page-item ${responseDTO.page == num ? "active" :""}
"><a class="page-link" data-num="${num}">${num}</a></li>
                                </c:forEach>
                                <%--                                    다음버튼표시--%>
                                <c:if test="${responseDTO.next}">
                                    <li class="page-item">
                                        <a class="page-link" data-num="${responseDTO.end + 1}">다음</a>
                                    </li>
                                </c:if>
                            </ul>
                            <script>
                                //클래스 명 : pagination 이용해서 요소를 선택하고
                                //이벤트 핸들러를 추가함
                                document.querySelector(".pagination").addEventListener("click", function (e) { //e:event
                                    //기본적인 기능을 방지하는 함수
                                    e.preventDefault()
                                    e.stopPropagation()

                                    const target = e.target

                                    //tagName의 이름이 A가 아니라면 함수를 나가고
                                    if (target.tagName !== 'A') {
                                        return
                                    }

                                    //tagName의 이름이 A이면 함수를 실행한다.
                                    //현재 페이지의 번호를 가져오기
                                    const num = target.getAttribute("data-num")

                                    const formObj = document.querySelector("form")

                                    formObj.innerHTML += `<input type="hidden" name="page" value='\${num}'>`
                                    formObj.submit;

                                    // 해당 페이지로 이동하기.
                                    // 스프링의 벡엔드 서버에 호출하면, 서버가 응답해서,
                                    // 해당 페이지로 리다이렉트 함. page 값과, size 을가지고
                                    // 정확히 하면 PageRequestDTO에 담아서 호출하고,
                                    // 서버는 PageResponseDTO에 담아서 화면에 보내고,
                                    // 화면은 해당 인스턴스 이용해서, 화면에 출력하는 형식.
                                    self.location = `/todo/list?page=\${num}`
                                }, false)

                                document.querySelector(".clearBtn").addEventListener("click", function (e) {
                                    e.preventDefault()
                                    e.stopPropagation()
                                    self.location = '/todo/list'
                                },false)
                            </script>
                            </nav>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row footer">
    <%--                <h1>Footer</h1>--%>
    <div class="row fixed-bottom" style="z-index: -100">
        <footer class="py-1 my-1">
            <p class="text-center text-muted">Footer</p>
        </footer>
    </div>
</div>
</div>

<!-- Optional JavaScript; choose one of the two! -->

<!-- Option 1: Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>

<!-- Option 2: Separate Popper and Bootstrap JS -->
<!--
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
-->
</body>
</html>